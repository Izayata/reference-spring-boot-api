package bar.imagine.demo.repository;

import java.util.List;
import java.util.UUID;

import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.outbox.OutboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailOutboxRepository extends JpaRepository<EmailOutbox, UUID> {
    List<EmailOutbox> findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus status);

    @Modifying
    @Query("UPDATE EmailOutbox eo SET eo.status = bar.imagine.demo.data.outbox.OutboxStatus.PENDING, eo.attempts = 0 "
        + "WHERE eo.status = bar.imagine.demo.data.outbox.OutboxStatus.FAILED")
    void requeueFailedEmails();
}
