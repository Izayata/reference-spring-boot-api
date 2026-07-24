package bar.imagine.demo.dto;

import static bar.imagine.demo.dto.MyUserRegistrationDTOTest.VALID_MY_USER_REGISTRATION_DTO;
import static bar.imagine.demo.dto.customer.AddressDTOTest.VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY;
import static bar.imagine.demo.dto.customer.PersonalDetailsDTOTest.VALID_PERSONAL_DETAILS_DTO;
import static bar.imagine.demo.util.MyUserRegistrationUtils.ERR_MSG_MY_USER_REGISTRATION_DTO_REQUIRED;
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

class RegistrationDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final RegistrationDTO VALID_REGISTRATION_DTO = RegistrationDTO.builder()
        .myUser(VALID_MY_USER_REGISTRATION_DTO)
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();

    private static final RegistrationDTO ERR_REGISTRATION_DTO_MY_USER_NULL = RegistrationDTO.builder()
        .myUser(null)
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();

    private static final RegistrationDTO ERR_REGISTRATION_DTO_PERSONAL_DETAILS_NULL = RegistrationDTO.builder()
        .myUser(VALID_MY_USER_REGISTRATION_DTO)
        .personalDetails(null)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();

    private static final RegistrationDTO ERR_REGISTRATION_DTO_SHIPPING_ADDRESS_NULL = RegistrationDTO.builder()
        .myUser(VALID_MY_USER_REGISTRATION_DTO)
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .shippingAddress(null)
        .billingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .build();

    private static final RegistrationDTO ERR_REGISTRATION_DTO_BILLING_ADDRESS_NULL = RegistrationDTO.builder()
        .myUser(VALID_MY_USER_REGISTRATION_DTO)
        .personalDetails(VALID_PERSONAL_DETAILS_DTO)
        .shippingAddress(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY)
        .billingAddress(null)
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
        void testRegistrationDtoValid() {
            Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(VALID_REGISTRATION_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testRegistrationDtoInvalidMyUserNull() {
            Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(ERR_REGISTRATION_DTO_MY_USER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_MY_USER_REGISTRATION_DTO_REQUIRED)));
        }

        @Test
        void testRegistrationDtoInvalidPersonalDetailsNull() {
            Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(ERR_REGISTRATION_DTO_PERSONAL_DETAILS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED)));
        }

        @Test
        void testRegistrationDtoInvalidShippingAddressNull() {
            Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(ERR_REGISTRATION_DTO_SHIPPING_ADDRESS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }

        @Test
        void testRegistrationDtoInvalidBillingAddressNull() {
            Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(ERR_REGISTRATION_DTO_BILLING_ADDRESS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ADDRESS_REQUIRED)));
        }
    }
}
