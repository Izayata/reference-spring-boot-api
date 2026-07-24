package bar.imagine.demo.dto;

import static bar.imagine.demo.dto.EmailDTOTest.VALID_EMAIL_DTO;
import static bar.imagine.demo.dto.customer.PersonalDetailsDTOTest.VALID_PERSONAL_DETAILS_DTO;
import static bar.imagine.demo.dto.customer.AddressDTOTest.VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.PersonalDetailsUtils.ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED;
import static bar.imagine.demo.util.customerUtils.AddressUtils.ERR_MSG_ADDRESS_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

public class CustomerDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final CustomerDTO ERR_CUSTOMER_DTO_PERSONAL_DETAILS_NULL = CustomerDTO.builder()
        .personalDetails(null)
        .email(VALID_EMAIL_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();
    private static final CustomerDTO ERR_CUSTOMER_DTO_PERSONAL_DETAILS_MISSING = CustomerDTO.builder()
        .email(VALID_EMAIL_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();
    private static final CustomerDTO ERR_CUSTOMER_DTO_EMAIL_NULL = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .email(null)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();
    private static final CustomerDTO ERR_CUSTOMER_DTO_EMAIL_MISSING = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();

    private static final CustomerDTO ERR_CUSTOMER_DTO_SHIPPING_ADDRESS_NULL = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .email(VALID_EMAIL_DTO)
        .shippingAddress(null)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();
    private static final CustomerDTO ERR_CUSTOMER_DTO_SHIPPING_ADDRESS_MISSING = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .email(VALID_EMAIL_DTO)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();
    private static final CustomerDTO ERR_CUSTOMER_DTO_BILLING_ADDRESS_NULL = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .email(VALID_EMAIL_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(null)
        .build();
    private static final CustomerDTO ERR_CUSTOMER_DTO_BILLING_ADDRESS_MISSING = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .email(VALID_EMAIL_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();
    public static final CustomerDTO VALID_CUSTOMER_DTO = CustomerDTO.builder()
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .email(VALID_EMAIL_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
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
        void testCustomerDtoValidRequiredFieldsOnly() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(VALID_CUSTOMER_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testCustomerDtoInvalidPersonalDetailsNull() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_PERSONAL_DETAILS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidPersonalDetailsMissing() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_PERSONAL_DETAILS_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidEmailNull() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_EMAIL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidEmailMissing() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_EMAIL_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidShippingAddressNull() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_SHIPPING_ADDRESS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidShippingAddressMissing() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_SHIPPING_ADDRESS_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidBillingAddressNull() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_BILLING_ADDRESS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }

        @Test
        void testCustomerDtoInvalidBillingAddressMissing() {
            Set<ConstraintViolation<CustomerDTO>> violations = validator.validate(ERR_CUSTOMER_DTO_BILLING_ADDRESS_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }
    }
}
