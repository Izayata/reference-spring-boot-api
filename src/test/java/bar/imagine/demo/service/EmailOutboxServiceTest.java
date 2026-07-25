package bar.imagine.demo.service;

import java.util.Optional;
import java.util.UUID;

import bar.imagine.demo.data.EmailOutbox;
import bar.imagine.demo.data.outbox.EmailType;
import bar.imagine.demo.data.outbox.OutboxStatus;
import bar.imagine.demo.exception.exceptions.EmailSendException;
import bar.imagine.demo.repository.EmailOutboxRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailOutboxServiceTest {

    @Mock private EmailOutboxRepository outboxRepository;
    @Mock private EmailService emailService;

    @InjectMocks
    private EmailOutboxService emailOutboxService;

    private EmailOutbox buildOutboxItem(UUID id, int attempts) {
        return EmailOutbox.builder()
            .id(id)
            .recipientEmail("recipient@example.com")
            .emailType(EmailType.REGISTRATION_SUCCESS)
            .subject("subject")
            .body("body")
            .status(OutboxStatus.PENDING)
            .attempts(attempts)
            .build();
    }

    @Test
    void processOne_marksSent_whenSendSucceeds() {
        UUID id = UUID.randomUUID();
        EmailOutbox item = buildOutboxItem(id, 0);
        when(outboxRepository.findById(id)).thenReturn(Optional.of(item));
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());

        emailOutboxService.processOne(id);

        assertEquals(OutboxStatus.SENT, item.getStatus());
        verify(outboxRepository).save(item);
    }

    @Test
    void processOne_incrementsAttempts_andStaysPending_whenSendFailsBelowMaxAttempts() {
        UUID id = UUID.randomUUID();
        EmailOutbox item = buildOutboxItem(id, 0);
        when(outboxRepository.findById(id)).thenReturn(Optional.of(item));
        doThrow(new EmailSendException("smtp failure")).when(emailService).sendEmail(anyString(), anyString(), anyString());

        emailOutboxService.processOne(id);

        assertEquals(OutboxStatus.PENDING, item.getStatus());
        assertEquals(1, item.getAttempts());
        verify(outboxRepository).save(item);
    }

    @Test
    void processOne_marksFailed_whenSendFailsAtMaxAttempts() {
        UUID id = UUID.randomUUID();
        EmailOutbox item = buildOutboxItem(id, 4);
        when(outboxRepository.findById(id)).thenReturn(Optional.of(item));
        doThrow(new EmailSendException("smtp failure")).when(emailService).sendEmail(anyString(), anyString(), anyString());

        emailOutboxService.processOne(id);

        assertEquals(OutboxStatus.FAILED, item.getStatus());
        assertEquals(5, item.getAttempts());
        verify(outboxRepository).save(item);
    }

    @Test
    void processOne_doesNothing_whenItemNotFound() {
        UUID id = UUID.randomUUID();
        when(outboxRepository.findById(id)).thenReturn(Optional.empty());

        emailOutboxService.processOne(id);

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(outboxRepository, never()).save(any());
    }

    @Test
    void processOne_doesNothing_whenItemAlreadySent() {
        UUID id = UUID.randomUUID();
        EmailOutbox item = buildOutboxItem(id, 0);
        item.setStatus(OutboxStatus.SENT);
        when(outboxRepository.findById(id)).thenReturn(Optional.of(item));

        emailOutboxService.processOne(id);

        verify(emailService, never()).sendEmail(anyString(), anyString(), anyString());
        verify(outboxRepository, never()).save(any());
    }
}
