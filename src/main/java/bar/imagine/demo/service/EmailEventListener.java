package bar.imagine.demo.service;

import bar.imagine.demo.exception.exceptions.EmailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailEventListener {
    private final EmailService emailService;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onRegistrationSuccess(RegistrationSuccessEvent event) {
        try {
            emailService.sendRegistrationSuccessfulEmail(event.email(), event.username());
        } catch (EmailSendException e) {
            log.error("Failed to send welcome email", e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPasswordResetRequested(PasswordResetRequestedEvent event) {
        try {
            emailService.sendPasswordResetEmail(event.recipientEmail(), event.recipientName(), event.resetUrl());
        } catch (EmailSendException e) {
            log.error("Failed to send password reset email", e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onOrderConfirmed(OrderConfirmedEvent event) {
        try {
            emailService.sendEmail(event.recipientEmail(), EmailService.ORDER_CONFIRMATION_SUBJECT, event.body());
        } catch (EmailSendException e) {
            log.error("Failed to send order confirmation email", e);
        }
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onPasswordChanged(PasswordChangedEvent event) {
        try {
            emailService.sendPasswordChangeConfirmationEmail(event.recipientEmail(), event.recipientName());
        } catch (EmailSendException e) {
            log.error("Failed to send password change confirmation email", e);
        }
    }
}
