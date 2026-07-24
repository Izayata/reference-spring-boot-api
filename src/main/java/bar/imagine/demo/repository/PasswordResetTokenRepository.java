package bar.imagine.demo.repository;


import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, UUID> {
    Optional<PasswordResetToken> findByUsedAtIsNullAndTokenLookupHash(String tokenLookupHash);

    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.expiresAt < :now")
    void deleteExpiredTokens(@Param("now") Instant now);

    @Modifying
    @Query("UPDATE PasswordResetToken prt SET prt.usedAt = :usedAt WHERE prt.myUser = :myUser AND prt.usedAt IS NULL")
    void invalidateAllMyUserTokens(@Param("myUser") MyUser myUser, @Param("usedAt") Instant usedAt);
}
