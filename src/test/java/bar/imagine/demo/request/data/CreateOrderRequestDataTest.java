package bar.imagine.demo.request.data;

import static bar.imagine.demo.dto.CustomerDTOTest.VALID_CUSTOMER_DTO;
import static bar.imagine.demo.util.request.data.CreateOrderRequestDataUtils.ERR_MSG_ORDER_ITEMS_EMPTY;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import bar.imagine.demo.data.order.PaymentType;
import bar.imagine.demo.request.data.createOrder.OrderItemRequestData;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CreateOrderRequestDataTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static OrderItemRequestData validOrderItem() {
        OrderItemRequestData item = new OrderItemRequestData();
        item.setFoodId(1L);
        item.setQuantity(1);
        return item;
    }

    private static CreateOrderRequestData build(boolean withCustomer, List<OrderItemRequestData> orderItems, PaymentType paymentType) {
        CreateOrderRequestData data = new CreateOrderRequestData();
        data.setCustomer(withCustomer ? VALID_CUSTOMER_DTO : null);
        data.setOrderItems(orderItems);
        data.setPaymentType(paymentType);
        return data;
    }

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }

    @Test
    void testValidCreateOrderRequestData() {
        Set<ConstraintViolation<CreateOrderRequestData>> violations =
            validator.validate(build(true, List.of(validOrderItem()), PaymentType.CASH));
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidCustomerNull() {
        Set<ConstraintViolation<CreateOrderRequestData>> violations =
            validator.validate(build(false, List.of(validOrderItem()), PaymentType.CASH));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("customer")));
    }

    @Test
    void testInvalidOrderItemsNull() {
        Set<ConstraintViolation<CreateOrderRequestData>> violations =
            validator.validate(build(true, null, PaymentType.CASH));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("orderItems")));
    }

    @Test
    void testInvalidOrderItemsEmpty() {
        Set<ConstraintViolation<CreateOrderRequestData>> violations =
            validator.validate(build(true, List.of(), PaymentType.CASH));
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ORDER_ITEMS_EMPTY)));
    }

    @Test
    void testInvalidPaymentTypeNull() {
        Set<ConstraintViolation<CreateOrderRequestData>> violations =
            validator.validate(build(true, List.of(validOrderItem()), null));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("paymentType")));
    }
}
