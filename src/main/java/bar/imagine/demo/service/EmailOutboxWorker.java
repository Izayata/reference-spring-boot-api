package bar.imagine.demo.service;

import java.util.List;

import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.outbox.OutboxStatus;
import bar.imagine.demo.repository.EmailOutboxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class EmailOutboxWorker {
    private final EmailOutboxRepository outboxRepository;
    private final EmailOutboxService emailOutboxService;

    @Scheduled(fixedRateString = "${app.email-outbox.poll-rate-ms:5000}")
    public void processPendingEmails() {
        List<EmailOutbox> pending = outboxRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);
        for (EmailOutbox item : pending) {
            emailOutboxService.processOne(item.getId());
        }
    }

    @Scheduled(cron = "${app.email-outbox.requeue-cron:0 0 3 * * *}")
    @Transactional
    public void requeueFailedEmails() {
        outboxRepository.requeueFailedEmails();
    }
}
