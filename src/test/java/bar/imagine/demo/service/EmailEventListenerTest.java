package bar.imagine.demo.service;

import bar.imagine.demo.exception.exceptions.EmailSendException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailEventListenerTest {

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EmailEventListener emailEventListener;

    @Test
    void onRegistrationSuccess_callsSendRegistrationSuccessfulEmail() {
        emailEventListener.onRegistrationSuccess(new RegistrationSuccessEvent("test@example.com", "testuser"));

        verify(emailService).sendRegistrationSuccessfulEmail("test@example.com", "testuser");
    }

    @Test
    void onOrderConfirmed_callsSendEmailWithConfirmationSubjectAndBody() {
        emailEventListener.onOrderConfirmed(new OrderConfirmedEvent("test@example.com", "order body"));

        verify(emailService).sendEmail("test@example.com", EmailService.ORDER_CONFIRMATION_SUBJECT, "order body");
    }

    @Test
    void onPasswordChanged_callsSendPasswordChangeConfirmationEmail() {
        emailEventListener.onPasswordChanged(new PasswordChangedEvent("test@example.com", "testuser"));

        verify(emailService).sendPasswordChangeConfirmationEmail("test@example.com", "testuser");
    }

    @Test
    void onPasswordResetRequested_callsSendPasswordResetEmail() {
        emailEventListener.onPasswordResetRequested(
            new PasswordResetRequestedEvent("test@example.com", "testuser", "http://localhost:3000/reset?token=abc"));

        verify(emailService).sendPasswordResetEmail("test@example.com", "testuser", "http://localhost:3000/reset?token=abc");
    }

    @Test
    void onRegistrationSuccess_swallowsEmailSendException() {
        doThrow(new EmailSendException("SMTP unavailable"))
            .when(emailService).sendRegistrationSuccessfulEmail("test@example.com", "testuser");

        assertDoesNotThrow(() ->
            emailEventListener.onRegistrationSuccess(new RegistrationSuccessEvent("test@example.com", "testuser")));
    }

    @Test
    void onOrderConfirmed_swallowsEmailSendException() {
        doThrow(new EmailSendException("SMTP unavailable"))
            .when(emailService).sendEmail("test@example.com", EmailService.ORDER_CONFIRMATION_SUBJECT, "order body");

        assertDoesNotThrow(() ->
            emailEventListener.onOrderConfirmed(new OrderConfirmedEvent("test@example.com", "order body")));
    }

    @Test
    void onPasswordChanged_swallowsEmailSendException() {
        doThrow(new EmailSendException("SMTP unavailable"))
            .when(emailService).sendPasswordChangeConfirmationEmail("test@example.com", "testuser");

        assertDoesNotThrow(() ->
            emailEventListener.onPasswordChanged(new PasswordChangedEvent("test@example.com", "testuser")));
    }

    @Test
    void onPasswordResetRequested_swallowsEmailSendException() {
        doThrow(new EmailSendException("SMTP unavailable"))
            .when(emailService).sendPasswordResetEmail("test@example.com", "testuser", "http://localhost:3000/reset?token=abc");

        assertDoesNotThrow(() -> emailEventListener.onPasswordResetRequested(
            new PasswordResetRequestedEvent("test@example.com", "testuser", "http://localhost:3000/reset?token=abc")));
    }
}
