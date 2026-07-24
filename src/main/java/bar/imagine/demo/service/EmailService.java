package bar.imagine.demo.service;

import bar.imagine.demo.data.Order;
import bar.imagine.demo.data.OrderItem;
import bar.imagine.demo.data.order.PaymentType;
import bar.imagine.demo.exception.exceptions.EmailSendException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;

    @Value("${app.name}")
    private String appName;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public static final String ORDER_CONFIRMATION_SUBJECT = "Rendelés visszaigazolás";

    public void sendRegistrationSuccessfulEmail(String recipientEmail, String recipientName) {
        String subject = "Sikeres regisztráció - " + appName;
        log.debug("Email subject set");
        StringBuilder sb = new StringBuilder();

        sb.append("Boldog napszakot, ").append(recipientName).append("!").append("\n")
            .append("\n")
            .append("Köszönjük, hogy megtisztel a bizalmával és regisztrált a rendszerünkbe!").append(" ")
            .append("Biztosíthatom, hogy kínálatunkban minden képzeletet felülmúló finomságokhoz lesz szerencséje ;)").append("\n")
            .append("A felhasználói fiók regisztrálása sikeresen megtörtént, jó étvágyat kívánunk!").append("\n")
            .append("\n")
            .append("Üdvözlettel, ImagineBar!").append("\n");
        log.debug("Email body prepared");

        this.sendEmail(recipientEmail, subject, sb.toString());
    }

    public void sendPasswordResetEmail(String recipientEmail, String recipientName, String resetUrl) {
        String subject = "Jelszó-visszaállítási kérelem - " + appName;
        StringBuilder sb = new StringBuilder();

        sb.append("Boldog napszakot, ").append(recipientName).append("!").append("\n")
            .append("\n")
            .append("A rendszerünk észlelte, hogy fiókjára jelszó-visszaállítási kérelem érkezett.").append(" ")
            .append("Amennyiben a jelszó-visszaállítási kérelmet nem Ön kérte, úgy kérjük, hogy ezt a levelet hagyja figyelmen kívül!").append("\n")
            .append("A jelszó-visszaállításhoz kattinton az alábbi linkre:").append("\n")
            .append(resetUrl).append("\n")
            .append("\n")
            .append("Üdvözlettel, ImagineBar!").append("\n");

        this.sendEmail(recipientEmail, subject, sb.toString());
    }

    public void sendPasswordChangeConfirmationEmail(String recipientEmail, String recipientName) {
        String subject = "Sikeres jelszó-visszaállítás megerősítése - " + appName;
        StringBuilder sb = new StringBuilder();

        sb.append("Boldog napszakot, ").append(recipientName).append("!").append("\n")
            .append("\n")
            .append("A jelszó-visszaállítás sikeresen megtörtént.").append(" ")
            .append("Amennyiben a jelszó-visszaállítást nem Ön végezte, úgy kérjük, hogy haladéktalanul vegye fel a kapcsolatot az ügyfélszolgálatunkkal!").append("\n")
            .append("\n")
            .append("Üdvözlettel, ImagineBar!").append("\n");

        this.sendEmail(recipientEmail, subject, sb.toString());
    }

    public String buildOrderConfirmationEmailBody(Order order, String authenticatedUsername) {
        String greeting = authenticatedUsername != null
            ? authenticatedUsername
            : order.getPersonalDetails().getFirstname().getValue();

        StringBuilder sb = new StringBuilder();
        sb.append("Kedves ").append(greeting).append("!\n")
            .append("\n")
            .append("Rendelésed sikeresen rögzítettük. A megrendelt ételekhez jó étvágyat kívánunk!\n\n")
            .append("A rendelés részletei az alábbiak:\n")
            .append(buildOrderItemsDetails(order.getOrderItems())).append("\n")
            .append("A rendelés teljes összege: ").append(order.getTotalCost().getAmount()).append(" Ft\n")
            .append("A fizetés módja: ").append(formatPaymentType(order.getPaymentType())).append("\n")
            .append("\n")
            .append("Üdvözlettel, ImagineBar csapata");
        return sb.toString();
    }

    public void sendEmail(String recipientEmail, String subject, String messageText) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(fromEmail);
        message.setTo(recipientEmail);
        message.setSubject(subject.trim());
        message.setText(messageText.trim());
        log.debug("Email properties configured, sending");

        try {
            javaMailSender.send(message);
        } catch (MailException e) {
            throw new EmailSendException("Failed to send email to " + recipientEmail + ": " + e.getMessage());
        }
    }

    private String buildOrderItemsDetails(java.util.List<OrderItem> orderItems) {
        StringBuilder details = new StringBuilder();
        for (OrderItem item : orderItems) {
            details
                .append("  ×").append(item.getQuantity()).append("| ").append(item.getFood().getFoodName().getValue())
                .append(" ").append(item.getOrderItemPrice().getAmount()).append(" Ft\n");
        }
        return details.toString();
    }

    private String formatPaymentType(PaymentType paymentType) {
        return switch (paymentType) {
            case CARD -> "Bankkártya";
            case CASH -> "Készpénz";
            default -> throw new IllegalStateException("Unexpected payment type: " + paymentType);
        };
    }
}
