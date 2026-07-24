package bar.imagine.demo.request.data.createOrder;

import static bar.imagine.demo.util.request.data.createOrder.OrderItemRequestDataUtils.QUANTITY_MAX_VALUE;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OrderItemRequestDataTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static OrderItemRequestData build(Long foodId, Integer quantity) {
        OrderItemRequestData data = new OrderItemRequestData();
        data.setFoodId(foodId);
        data.setQuantity(quantity);
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
    void testValidOrderItemRequestData() {
        Set<ConstraintViolation<OrderItemRequestData>> violations = validator.validate(build(1L, 1));
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidFoodIdNull() {
        Set<ConstraintViolation<OrderItemRequestData>> violations = validator.validate(build(null, 1));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("foodId")));
    }

    @Test
    void testInvalidQuantityNull() {
        Set<ConstraintViolation<OrderItemRequestData>> violations = validator.validate(build(1L, null));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testInvalidQuantityZero() {
        Set<ConstraintViolation<OrderItemRequestData>> violations = validator.validate(build(1L, 0));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testInvalidQuantityNegative() {
        Set<ConstraintViolation<OrderItemRequestData>> violations = validator.validate(build(1L, -1));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }

    @Test
    void testInvalidQuantityTooHigh() {
        Set<ConstraintViolation<OrderItemRequestData>> violations = validator.validate(build(1L, QUANTITY_MAX_VALUE + 1));
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("quantity")));
    }
}
