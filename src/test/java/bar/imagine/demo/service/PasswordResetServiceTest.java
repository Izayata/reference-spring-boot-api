package bar.imagine.demo.service;

import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.PasswordResetToken;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.dto.EmailDTO;
import bar.imagine.demo.dto.NewPasswordDetailsDTO;
import bar.imagine.demo.dto.TokenValidationResult;
import bar.imagine.demo.dto.myUser.MyUsernameDTO;
import bar.imagine.demo.dto.myUser.PasswordDTO;
import bar.imagine.demo.exception.exceptions.InvalidPasswordException;
import bar.imagine.demo.exception.exceptions.RateLimitExceededException;
import bar.imagine.demo.exception.exceptions.TokenAlreadyUsedException;
import bar.imagine.demo.exception.exceptions.TokenExpiredException;
import bar.imagine.demo.exception.exceptions.TokenNotFoundException;
import bar.imagine.demo.repository.EmailOutboxRepository;
import bar.imagine.demo.repository.MyUserRepository;
import bar.imagine.demo.repository.PasswordResetTokenRepository;
import bar.imagine.demo.request.data.ForgottenPasswordRequestData;
import bar.imagine.demo.request.data.ResetPasswordRequestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordResetServiceTest {

    @Mock private PasswordResetTokenRepository tokenRepository;
    @Mock private MyUserRepository myUserRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private RedisService redisService;
    @Mock private EmailOutboxRepository emailOutboxRepository;
    @Mock private EmailService emailService;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(passwordResetService, "frontendUrl", "http://localhost:3000");
        ReflectionTestUtils.setField(passwordResetService, "maxRequestsPerHour", 3);
    }

    private ForgottenPasswordRequestData buildRequest(String email, String username) {
        return new ForgottenPasswordRequestData(new EmailDTO(email), new MyUsernameDTO(username));
    }

    private MyUser buildUser(String username, String email) {
        return MyUser.builder()
            .myUsername(new MyUsername(username))
            .email(new Email(email))
            .build();
    }

    private MyUser buildUserWithPassword(String username, String email, String currentPasswordHash) {
        return MyUser.builder()
            .myUsername(new MyUsername(username))
            .email(new Email(email))
            .password(new Password(currentPasswordHash))
            .build();
    }

    private PasswordResetToken buildResetToken(MyUser myUser, String tokenHash, boolean expired, boolean used) {
        return PasswordResetToken.builder()
            .myUser(myUser)
            .tokenHash(tokenHash)
            .tokenLookupHash("anylookup")
            .expiresAt(expired ? Instant.now().minus(Duration.ofMinutes(1)) : Instant.now().plus(Duration.ofMinutes(15)))
            .usedAt(used ? Instant.now() : null)
            .build();
    }

    private ResetPasswordRequestData buildResetRequest(String token, String newPassword) {
        return ResetPasswordRequestData.builder()
            .token(token)
            .newPasswordDetails(NewPasswordDetailsDTO.builder()
                .newPassword(new PasswordDTO(newPassword))
                .confirmNewPassword(new PasswordDTO(newPassword))
                .build())
            .build();
    }

    @Test
    void requestPasswordReset_silentReturn_whenEmailNotFound() {
        when(myUserRepository.findByEmail(any(Email.class))).thenReturn(Optional.empty());

        String result = passwordResetService.requestPasswordReset(
            buildRequest("unknown@example.com", "nobody"));

        assertTrue(result.contains("If the email"));
        verify(tokenRepository, never()).save(any());
        verify(emailOutboxRepository, never()).save(any());
    }

    @Test
    void requestPasswordReset_throwsRateLimit_whenLimitExceeded() {
        MyUser user = buildUser("testuser", "test@example.com");
        when(myUserRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        when(redisService.atomicIncrementWithTtlOnFirstWrite(anyString(), any(Duration.class))).thenReturn(4L);

        assertThrows(RateLimitExceededException.class, () ->
            passwordResetService.requestPasswordReset(
                buildRequest("test@example.com", "testuser")));
    }

    @Test
    void requestPasswordReset_sendsEmail_whenEmailAndUsernameMatch() {
        MyUser user = buildUser("testuser", "test@example.com");
        when(myUserRepository.findByEmail(any(Email.class))).thenReturn(Optional.of(user));
        when(redisService.atomicIncrementWithTtlOnFirstWrite(anyString(), any(Duration.class))).thenReturn(1L);
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$someHash");
        when(emailService.buildPasswordResetEmail(anyString(), anyString())).thenReturn(new EmailContent("subject", "body"));

        String result = passwordResetService.requestPasswordReset(
            buildRequest("test@example.com", "testuser"));

        verify(tokenRepository).save(any(PasswordResetToken.class));
        verify(emailOutboxRepository).save(any(EmailOutbox.class));
        assertTrue(result.contains("If the email"));
    }

    @Test
    void validateResetToken_returnsValid_forActiveToken() {
        PasswordResetToken token = PasswordResetToken.builder()
            .myUser(buildUser("user", "u@example.com"))
            .tokenHash("$2a$10$someHash")
            .tokenLookupHash("anylookup")
            .expiresAt(Instant.now().plus(Duration.ofMinutes(15)))
            .build();

        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        TokenValidationResult result = passwordResetService.validateResetToken("someRawToken");

        assertTrue(result.isValid());
        assertFalse(result.isExpired());
        assertFalse(result.isUsed());
    }

    @Test
    void setNewPassword_succeeds_whenTokenValidAndPasswordDiffers() {
        MyUser user = buildUserWithPassword("testuser", "test@example.com", "$2a$10$oldHash");
        PasswordResetToken token = buildResetToken(user, "$2a$10$tokenHash", false, false);

        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(eq("someRawToken"), eq("$2a$10$tokenHash"))).thenReturn(true);
        when(passwordEncoder.matches(eq("NewPassword@1"), eq("$2a$10$oldHash"))).thenReturn(false);
        when(passwordEncoder.encode("NewPassword@1")).thenReturn("$2a$10$newHash");
        when(emailService.buildPasswordChangeConfirmationEmail(anyString())).thenReturn(new EmailContent("subject", "body"));

        String result = passwordResetService.setNewPassword(buildResetRequest("someRawToken", "NewPassword@1"));

        assertTrue(result.contains("successfully"));
        verify(myUserRepository).save(user);
        assertEquals("$2a$10$newHash", user.getPassword().getValue());
        verify(tokenRepository).save(token);
        assertTrue(token.isUsed());
        verify(tokenRepository).invalidateAllMyUserTokens(eq(user), any(Instant.class));
        verify(emailOutboxRepository).save(any(EmailOutbox.class));
    }

    @Test
    void setNewPassword_throwsTokenNotFound_whenLookupHashNotFound() {
        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.empty());

        assertThrows(TokenNotFoundException.class, () ->
            passwordResetService.setNewPassword(buildResetRequest("someRawToken", "NewPassword@1")));
        verify(myUserRepository, never()).save(any());
    }

    @Test
    void setNewPassword_throwsTokenNotFound_whenBCryptCompareFails() {
        MyUser user = buildUserWithPassword("testuser", "test@example.com", "$2a$10$oldHash");
        PasswordResetToken token = buildResetToken(user, "$2a$10$tokenHash", false, false);

        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(eq("someRawToken"), eq("$2a$10$tokenHash"))).thenReturn(false);

        assertThrows(TokenNotFoundException.class, () ->
            passwordResetService.setNewPassword(buildResetRequest("someRawToken", "NewPassword@1")));
        verify(myUserRepository, never()).save(any());
    }

    @Test
    void setNewPassword_throwsTokenExpired_whenTokenExpired() {
        MyUser user = buildUserWithPassword("testuser", "test@example.com", "$2a$10$oldHash");
        PasswordResetToken token = buildResetToken(user, "$2a$10$tokenHash", true, false);

        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(eq("someRawToken"), eq("$2a$10$tokenHash"))).thenReturn(true);

        assertThrows(TokenExpiredException.class, () ->
            passwordResetService.setNewPassword(buildResetRequest("someRawToken", "NewPassword@1")));
        verify(myUserRepository, never()).save(any());
    }

    @Test
    void setNewPassword_throwsTokenAlreadyUsed_whenTokenUsed() {
        MyUser user = buildUserWithPassword("testuser", "test@example.com", "$2a$10$oldHash");
        PasswordResetToken token = buildResetToken(user, "$2a$10$tokenHash", false, true);

        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(eq("someRawToken"), eq("$2a$10$tokenHash"))).thenReturn(true);

        assertThrows(TokenAlreadyUsedException.class, () ->
            passwordResetService.setNewPassword(buildResetRequest("someRawToken", "NewPassword@1")));
        verify(myUserRepository, never()).save(any());
    }

    @Test
    void setNewPassword_throwsInvalidPassword_whenNewPasswordSameAsOld() {
        MyUser user = buildUserWithPassword("testuser", "test@example.com", "$2a$10$oldHash");
        PasswordResetToken token = buildResetToken(user, "$2a$10$tokenHash", false, false);

        when(tokenRepository.findByUsedAtIsNullAndTokenLookupHash(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(eq("someRawToken"), eq("$2a$10$tokenHash"))).thenReturn(true);
        when(passwordEncoder.matches(eq("OldPassword@1"), eq("$2a$10$oldHash"))).thenReturn(true);

        assertThrows(InvalidPasswordException.class, () ->
            passwordResetService.setNewPassword(buildResetRequest("someRawToken", "OldPassword@1")));
        verify(myUserRepository, never()).save(any());
        verify(emailOutboxRepository, never()).save(any());
    }
}
