package bar.imagine.demo.repository;

import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.PasswordResetToken;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class PasswordResetTokenRepositoryTest {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    private MyUser savedUser;

    @BeforeEach
    void setUp() {
        savedUser = myUserRepository.save(MyUser.builder()
            .myUsername(new MyUsername("resetuser"))
            .password(new Password("$2a$10$WQ/oHjOGmSmkEjwEwuPpUeQKuNVB8VCVHpMhxdQnZ3fVGnOmJ3gUW"))
            .email(new Email("reset@example.com"))
            .build());
    }

    private PasswordResetToken buildToken(String lookupHash, Instant expiresAt, Instant usedAt) {
        return PasswordResetToken.builder()
            .myUser(savedUser)
            .tokenHash("storedBcryptHash")
            .tokenLookupHash(lookupHash)
            .expiresAt(expiresAt)
            .usedAt(usedAt)
            .build();
    }

    @Test
    void findByUsedAtIsNullAndTokenLookupHash_returnsToken_whenUnused() {
        tokenRepository.save(buildToken("abc123", Instant.now().plus(Duration.ofMinutes(15)), null));
        Optional<PasswordResetToken> found = tokenRepository.findByUsedAtIsNullAndTokenLookupHash("abc123");
        assertTrue(found.isPresent());
    }

    @Test
    void findByUsedAtIsNullAndTokenLookupHash_returnsEmpty_whenUsed() {
        tokenRepository.save(buildToken("used999", Instant.now().plus(Duration.ofMinutes(15)), Instant.now()));
        Optional<PasswordResetToken> found = tokenRepository.findByUsedAtIsNullAndTokenLookupHash("used999");
        assertFalse(found.isPresent());
    }

    @Test
    void deleteExpiredTokens_removesExpiredToken_keepsValidToken() {
        tokenRepository.save(buildToken("expired", Instant.now().minus(Duration.ofMinutes(1)), null));
        tokenRepository.save(buildToken("valid", Instant.now().plus(Duration.ofMinutes(15)), null));

        tokenRepository.deleteExpiredTokens(Instant.now());

        assertFalse(tokenRepository.findByUsedAtIsNullAndTokenLookupHash("expired").isPresent());
        assertTrue(tokenRepository.findByUsedAtIsNullAndTokenLookupHash("valid").isPresent());
    }

    @Test
    void invalidateAllMyUserTokens_setsUsedAt_onAllUnusedTokens() {
        tokenRepository.save(buildToken("token1", Instant.now().plus(Duration.ofMinutes(15)), null));
        tokenRepository.save(buildToken("token2", Instant.now().plus(Duration.ofMinutes(15)), null));

        tokenRepository.invalidateAllMyUserTokens(savedUser, Instant.now());

        assertFalse(tokenRepository.findByUsedAtIsNullAndTokenLookupHash("token1").isPresent());
        assertFalse(tokenRepository.findByUsedAtIsNullAndTokenLookupHash("token2").isPresent());
    }
}
