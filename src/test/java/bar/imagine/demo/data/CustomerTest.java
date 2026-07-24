package bar.imagine.demo.data;

import static bar.imagine.demo.data.EmailTest.VALID_EMAIL;
import static bar.imagine.demo.data.customer.AddressTest.VALID_ADDRESS_REQUIRED_FIELDS_ONLY;
import static bar.imagine.demo.data.customer.PersonalDetailsTest.VALID_PERSONAL_DETAILS;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.PersonalDetailsUtils.ERR_MSG_PERSONAL_DETAILS_REQUIRED;
import static bar.imagine.demo.util.customerUtils.AddressUtils.ERR_MSG_ADDRESS_REQUIRED;
import static bar.imagine.demo.util.customerUtils.DefaultShippingAddressUtils.ERR_MSG_DEFAULT_SHIPPING_ADDRESS_REQUIRED;
import static bar.imagine.demo.util.customerUtils.ShippingAddressesUtils.ERR_MSG_SHIPPING_ADDRESSES_EMPTY;
import static bar.imagine.demo.util.customerUtils.ShippingAddressesUtils.ERR_MSG_SHIPPING_ADDRESSES_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class CustomerTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Customer ERR_CUSTOMER_PERSONAL_DETAILS_NULL = Customer.builder()
        .personalDetails(null)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_PERSONAL_DETAILS_MISSING = Customer.builder()
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_EMAIL_NULL = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(null)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_EMAIL_MISSING = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();

    private static final Customer ERR_CUSTOMER_BILLING_ADDRESS_NULL = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(null)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_BILLING_ADDRESS_MISSING = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_SHIPPING_ADDRESSES_EMPTY = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>())
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_SHIPPING_ADDRESSES_NULL = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(null)
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_SHIPPING_ADDRESSES_MISSING = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();
    private static final Customer ERR_CUSTOMER_DEFAULT_SHIPPING_ADDRESS_NULL = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(null)
        .build();
    private static final Customer ERR_CUSTOMER_DEFAULT_SHIPPING_ADDRESS_MISSING = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .build();
    public static final Customer VALID_CUSTOMER = Customer.builder()
        .personalDetails(VALID_PERSONAL_DETAILS)
        .email(VALID_EMAIL)
        .billingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .shippingAddresses(new ArrayList<>(List.of(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)))
        .defaultShippingAddress(VALID_ADDRESS_REQUIRED_FIELDS_ONLY)
        .build();

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }


    @Nested
    @DisplayName("Valid")
    class Valid {

        @Test
        void testCustomerValidRequiredFieldsOnly() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(VALID_CUSTOMER);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testCustomerInvalidPersonalDetailsNull() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_PERSONAL_DETAILS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PERSONAL_DETAILS_REQUIRED)));
        }

        @Test
        void testCustomerInvalidPersonalDetailsMissing() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_PERSONAL_DETAILS_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PERSONAL_DETAILS_REQUIRED)));
        }

        @Test
        void testCustomerInvalidEmailNull() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_EMAIL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testCustomerInvalidEmailMissing() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_EMAIL_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testCustomerInvalidBillingAddressNull() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_BILLING_ADDRESS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }

        @Test
        void testCustomerInvalidBillingAddressMissing() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_BILLING_ADDRESS_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }

        @Test
        void testCustomerInvalidShippingAddressesEmpty() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_SHIPPING_ADDRESSES_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_SHIPPING_ADDRESSES_EMPTY)));
        }

        @Test
        void testCustomerInvalidShippingAddressesNull() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_SHIPPING_ADDRESSES_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_SHIPPING_ADDRESSES_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_SHIPPING_ADDRESSES_EMPTY)));
        }

        @Test
        void testCustomerInvalidShippingAddressesMissing() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_SHIPPING_ADDRESSES_MISSING);
            // @Builder.Default initialises shippingAddresses to new ArrayList<>(), so null violation does not fire
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_SHIPPING_ADDRESSES_EMPTY)));
        }

        @Test
        void testCustomerInvalidDefaultShippingAddressNull() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_DEFAULT_SHIPPING_ADDRESS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DEFAULT_SHIPPING_ADDRESS_REQUIRED)));
        }

        @Test
        void testCustomerInvalidDefaultShippingAddressMissing() {
            Set<ConstraintViolation<Customer>> violations = validator.validate(ERR_CUSTOMER_DEFAULT_SHIPPING_ADDRESS_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DEFAULT_SHIPPING_ADDRESS_REQUIRED)));
        }
    }
}
