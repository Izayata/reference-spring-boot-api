package bar.imagine.demo.service;

import java.util.List;
import java.util.UUID;

import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.outbox.EmailType;
import bar.imagine.demo.data.outbox.OutboxStatus;
import bar.imagine.demo.repository.EmailOutboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailOutboxWorkerTest {

    @Mock private EmailOutboxRepository outboxRepository;
    @Mock private EmailOutboxService emailOutboxService;

    @InjectMocks
    private EmailOutboxWorker emailOutboxWorker;

    private EmailOutbox buildOutboxItem(UUID id) {
        return EmailOutbox.builder()
            .id(id)
            .recipientEmail("recipient@example.com")
            .emailType(EmailType.REGISTRATION_SUCCESS)
            .subject("subject")
            .body("body")
            .build();
    }

    @Test
    void processPendingEmails_callsProcessOne_forEachPendingRow() {
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();
        when(outboxRepository.findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING))
            .thenReturn(List.of(buildOutboxItem(firstId), buildOutboxItem(secondId)));

        emailOutboxWorker.processPendingEmails();

        verify(emailOutboxService).processOne(eq(firstId));
        verify(emailOutboxService).processOne(eq(secondId));
    }

    @Test
    void requeueFailedEmails_delegatesToRepository() {
        emailOutboxWorker.requeueFailedEmails();

        verify(outboxRepository).requeueFailedEmails();
    }
}
