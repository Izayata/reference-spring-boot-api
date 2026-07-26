package bar.imagine.demo.data;

import java.time.Instant;
import java.util.UUID;

import bar.imagine.demo.data.outbox.EmailType;
import bar.imagine.demo.data.outbox.OutboxStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "email_outbox")
public class EmailOutbox {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "RECIPIENT_EMAIL", nullable = false)
    private String recipientEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "EMAIL_TYPE", nullable = false)
    private EmailType emailType;

    @Column(name = "SUBJECT", nullable = false)
    private String subject;

    @Column(name = "BODY", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    @Builder.Default
    private OutboxStatus status = OutboxStatus.PENDING;

    @Setter
    @Column(name = "ATTEMPTS", nullable = false)
    @Builder.Default
    private int attempts = 0;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Instant createdAt;

    @Setter
    @Column(name = "LAST_ATTEMPT_AT")
    private Instant lastAttemptAt;
}
