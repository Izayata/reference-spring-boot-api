package bar.imagine.demo.service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.HexFormat;
import java.util.Optional;
import java.util.UUID;

import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.PasswordResetToken;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.dto.TokenValidationResult;
import bar.imagine.demo.exception.exceptions.InvalidPasswordException;
import bar.imagine.demo.exception.exceptions.RateLimitExceededException;
import bar.imagine.demo.exception.exceptions.TokenAlreadyUsedException;
import bar.imagine.demo.exception.exceptions.TokenExpiredException;
import bar.imagine.demo.exception.exceptions.TokenNotFoundException;
import bar.imagine.demo.repository.MyUserRepository;
import bar.imagine.demo.repository.PasswordResetTokenRepository;
import bar.imagine.demo.request.data.ForgottenPasswordRequestData;
import bar.imagine.demo.request.data.ResetPasswordRequestData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PasswordResetService {
    private final PasswordResetTokenRepository tokenRepository;
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final ApplicationEventPublisher eventPublisher;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Value("${app.password-reset.token-expiry-minutes:15}")
    private int tokenExpiryMinutes;

    @Value("${app.password-reset.max-requests-per-hour:3}")
    private int maxRequestsPerHour;

    private static final String RATE_LIMIT_KEY_PREFIX = "password_reset_rate_limit:";

    public String requestPasswordReset(ForgottenPasswordRequestData requestData) {
        String email = requestData.getEmail().getValue();
        String myUsername = requestData.getMyUsername().getValue();

        Optional<MyUser> myUserOpt = myUserRepository.findByEmail(new Email(email));
        if (myUserOpt.isPresent()) {
            log.debug("User found with email address, checking rate limit");

            if (incrementAndGetRateLimitCount(email) > maxRequestsPerHour) {
                throw new RateLimitExceededException("Attempts exceeded! Try again in an hour!");
            }
            log.debug("Rate limit passed, checking username match");

            MyUser myUser = myUserOpt.get();

            if (myUsername.equals(myUser.getMyUsername().getValue())) {
                log.debug("Username matches, invalidating existing tokens");

                tokenRepository.invalidateAllMyUserTokens(myUser, Instant.now());
                log.debug("Existing tokens invalidated");

                String rawToken = generateSecureToken();
                log.debug("Password reset token generated");

                String tokenHash = hashToken(rawToken);
                log.debug("Token hash stored");

                PasswordResetToken resetToken = PasswordResetToken
                    .builder()
                    .myUser(myUser)
                    .tokenHash(tokenHash)
                    .tokenLookupHash(computeLookupHash(rawToken))
                    .expiresAt(Instant.now().plus(Duration.ofMinutes(tokenExpiryMinutes)))
                    .build();

                tokenRepository.save(resetToken);
                log.debug("Password reset token saved to repository");

                String resetUrl = frontendUrl + "/forgot-password?token=" + rawToken;
                eventPublisher.publishEvent(new PasswordResetRequestedEvent(
                    myUser.getEmail().getValue(), myUser.getMyUsername().getValue(), resetUrl));
                log.debug("Password reset email event published");
            }
        }

        return "If the email address is registered, a password reset link has been sent to it!";
    }

    @Transactional(readOnly = true)
    public TokenValidationResult validateResetToken(String rawToken) {
        log.debug("Validating reset token");

        PasswordResetToken token = getPasswordResetTokenByRawToken(rawToken);
        log.debug("Token located");

        if (token.isExpired()) {
            throw new TokenExpiredException("The reset link has expired!");
        }
        log.debug("Token is not expired, checking used status");

        if (token.isUsed()) {
            throw new TokenAlreadyUsedException("The reset link has already been used!");
        }
        log.debug("Token is valid");

        return new TokenValidationResult(true, false, false, "The reset link is valid!");
    }

    public String setNewPassword(ResetPasswordRequestData requestData) {
        String rawToken = requestData.getToken();
        String newPassword = requestData.getNewPasswordDetails().getNewPassword().getValue();

        PasswordResetToken token = getPasswordResetTokenByRawToken(rawToken);
        log.debug("Token located for password reset");

        if (token.isExpired()) {
            throw new TokenExpiredException("The reset link has expired!");
        }
        if (token.isUsed()) {
            throw new TokenAlreadyUsedException("The reset link has already been used!");
        }
        log.debug("Token is valid, checking password difference");

        MyUser myUser = token.getMyUser();
        if (passwordEncoder.matches(newPassword, myUser.getPassword().getValue())) {
            throw new InvalidPasswordException("The new password cannot be the same as the old password!");
        }
        log.debug("New password differs from current, updating");

        myUser.setPassword(new Password(passwordEncoder.encode(newPassword)));
        myUserRepository.save(myUser);
        log.debug("Password updated and saved");

        token.markAsUsed();
        tokenRepository.save(token);
        log.debug("Token marked as used");

        tokenRepository.invalidateAllMyUserTokens(myUser, Instant.now());
        log.debug("All user tokens invalidated");

        eventPublisher.publishEvent(new PasswordChangedEvent(myUser.getEmail().getValue(), myUser.getMyUsername().getValue()));
        log.debug("Password changed event published");

        return "Password has been successfully reset!";
    }

    private String generateSecureToken() {
        String fullToken = UUID.randomUUID() + "-" + UUID.randomUUID();
        return fullToken.substring(0, Math.min(fullToken.length(), 72));
    }

    private String hashToken(String rawToken) {
        return passwordEncoder.encode(rawToken);
    }

    private boolean compareToken(String rawToken, String tokenHash) {
        log.debug("Comparing token against stored hash");
        return passwordEncoder.matches(rawToken, tokenHash);
    }

    private long incrementAndGetRateLimitCount(String email) {
        String key = RATE_LIMIT_KEY_PREFIX + email;
        long attempts = redisService.atomicIncrementWithTtlOnFirstWrite(key, Duration.ofHours(1));
        log.debug("Rate limit attempts after atomic increment: {}", attempts);
        return attempts;
    }

    private PasswordResetToken getPasswordResetTokenByRawToken(String rawToken) {
        String lookupHash = computeLookupHash(rawToken);
        PasswordResetToken token = tokenRepository
                .findByUsedAtIsNullAndTokenLookupHash(lookupHash)
                .orElseThrow(() -> new TokenNotFoundException("Invalid reset token!"));
        // BCrypt verify for defense in depth (SHA-256 is for fast indexed lookup only)
        if (!compareToken(rawToken, token.getTokenHash())) {
            throw new TokenNotFoundException("Invalid reset token!");
        }
        return token;
    }

    private String computeLookupHash(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }

    @Scheduled(fixedRateString = "${app.password-reset.cleanup-rate-ms:3600000}")
    public void cleanupExpiredTokens() {
        tokenRepository.deleteExpiredTokens(Instant.now());
    }
}
