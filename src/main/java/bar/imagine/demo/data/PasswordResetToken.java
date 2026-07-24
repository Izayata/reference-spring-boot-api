package bar.imagine.demo.data;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "password_reset_tokens", indexes = {
    @Index(name = "idx_prt_lookup_hash", columnList = "TOKEN_LOOKUP_HASH"),
    @Index(name = "idx_prt_myuser_id", columnList = "MYUSER_ID")
})
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MYUSER_ID", nullable = false)
    @ToString.Exclude
    private MyUser myUser;

    @Column(name = "TOKEN_HASH", nullable = false)
    @ToString.Exclude
    private String tokenHash;

    @Column(name = "TOKEN_LOOKUP_HASH", nullable = false)
    @ToString.Exclude
    private String tokenLookupHash;

    @Column(name = "EXPIRES_AT", nullable = false)
    private Instant expiresAt;

    @Setter
    @Column(name = "USED_AT")
    private Instant usedAt;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Instant createdAt;

    public boolean isExpired() {
        return Instant.now().isAfter(expiresAt);
    }

    public void markAsUsed() {
        this.usedAt = Instant.now();
    }

    public boolean isUsed() {
        return usedAt != null;
    }
}
