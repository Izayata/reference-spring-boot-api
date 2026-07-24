package bar.imagine.demo.service;

import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.Order;
import bar.imagine.demo.data.OrderItem;
import bar.imagine.demo.data.customer.PersonalDetails;
import bar.imagine.demo.data.customer.personalDetails.Firstname;
import bar.imagine.demo.data.customer.personalDetails.Lastname;
import bar.imagine.demo.data.customer.personalDetails.PhoneNumber;
import bar.imagine.demo.data.food.FoodName;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.data.order.PaymentType;
import bar.imagine.demo.exception.exceptions.EmailSendException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "appName", "ImagineBar");
        ReflectionTestUtils.setField(emailService, "fromEmail", "no-reply@imaginebar.hu");
    }

    private OrderItem buildOrderItem(String foodName, int quantity, String amount) {
        Food food = Food.builder().foodName(new FoodName(foodName)).build();
        return OrderItem.builder()
            .food(food)
            .quantity(quantity)
            .orderItemPrice(new Price(new BigDecimal(amount), CurrencyEnum.HUF))
            .build();
    }

    private Order buildOrder(PaymentType paymentType, Customer customer, PersonalDetails guestDetails) {
        return Order.builder()
            .personalDetails(guestDetails)
            .customer(customer)
            .paymentType(paymentType)
            .totalCost(new Price(new BigDecimal("3500"), CurrencyEnum.HUF))
            .orderItems(List.of(buildOrderItem("Gulyásleves", 2, "1200")))
            .build();
    }

    @Test
    void buildOrderConfirmationEmailBody_usesUsername_whenAuthenticated() {
        Order order = buildOrder(PaymentType.CARD, null, null);

        String body = emailService.buildOrderConfirmationEmailBody(order, "testuser");

        assertTrue(body.contains("testuser"));
        assertTrue(body.contains("Bankkártya"));
        assertTrue(body.contains("Gulyásleves"));
    }

    @Test
    void buildOrderConfirmationEmailBody_usesGuestFirstname_whenNotAuthenticated() {
        PersonalDetails guestDetails = PersonalDetails.builder()
            .firstname(new Firstname("Béla"))
            .lastname(new Lastname("Kovács"))
            .phoneNumber(new PhoneNumber("+36301234567"))
            .build();
        Order order = buildOrder(PaymentType.CASH, null, guestDetails);

        String body = emailService.buildOrderConfirmationEmailBody(order, null);

        assertTrue(body.contains("Béla"));
        assertTrue(body.contains("Készpénz"));
    }

    @Test
    void sendEmail_sendsSimpleMailMessage_withGivenFields() {
        ArgumentCaptor<SimpleMailMessage> captor = ArgumentCaptor.forClass(SimpleMailMessage.class);

        emailService.sendEmail("recipient@example.com", "Subject", "Body");

        verify(javaMailSender).send(captor.capture());
        SimpleMailMessage sent = captor.getValue();
        assertTrue(sent.getTo() != null && sent.getTo()[0].equals("recipient@example.com"));
        assertTrue(sent.getSubject().equals("Subject"));
        assertTrue(sent.getText().equals("Body"));
    }

    @Test
    void sendEmail_throwsEmailSendException_whenMailSenderFails() {
        doThrow(new MailSendException("smtp failure")).when(javaMailSender).send(any(SimpleMailMessage.class));

        assertThrows(EmailSendException.class, () ->
            emailService.sendEmail("recipient@example.com", "Subject", "Body"));
    }
}
