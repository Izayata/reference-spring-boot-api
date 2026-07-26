package bar.imagine.demo.service;

import java.time.Instant;
import java.util.UUID;

import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.outbox.OutboxStatus;
import bar.imagine.demo.exception.exceptions.EmailSendException;
import bar.imagine.demo.repository.EmailOutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailOutboxService {
    private static final int MAX_ATTEMPTS = 5;

    private final EmailOutboxRepository outboxRepository;
    private final EmailService emailService;

    @Transactional
    public void processOne(UUID id) {
        EmailOutbox outboxItem = outboxRepository.findById(id).orElse(null);
        if (outboxItem == null || outboxItem.getStatus() != OutboxStatus.PENDING) {
            return;
        }

        outboxItem.setLastAttemptAt(Instant.now());
        try {
            emailService.sendEmail(outboxItem.getRecipientEmail(), outboxItem.getSubject(), outboxItem.getBody());
            outboxItem.setStatus(OutboxStatus.SENT);
        } catch (EmailSendException e) {
            outboxItem.setAttempts(outboxItem.getAttempts() + 1);
            if (outboxItem.getAttempts() >= MAX_ATTEMPTS) {
                outboxItem.setStatus(OutboxStatus.FAILED);
                log.error("Giving up sending email id={} to {} after {} attempts", id, outboxItem.getRecipientEmail(), outboxItem.getAttempts(), e);
            } else {
                log.warn("Failed attempt {} sending email id={} to {}", outboxItem.getAttempts(), id, outboxItem.getRecipientEmail(), e);
            }
        }

        outboxRepository.save(outboxItem);
    }
}
