package bar.imagine.demo.data.customer;

import static bar.imagine.demo.data.customer.personalDetails.FirstnameTest.VALID_FIRSTNAME;
import static bar.imagine.demo.data.customer.personalDetails.LastnameTest.VALID_LASTNAME;
import static bar.imagine.demo.data.customer.personalDetails.PhoneNumberTest.VALID_PHONE_NUMBER_INTERNATIONAL_FORMAT;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_REQUIRED;
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

public class PersonalDetailsTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    public static final PersonalDetails VALID_PERSONAL_DETAILS = PersonalDetails.builder()
        .firstname(VALID_FIRSTNAME)
        .lastname(VALID_LASTNAME)
        .phoneNumber(VALID_PHONE_NUMBER_INTERNATIONAL_FORMAT)
        .build();

    private static final PersonalDetails ERR_PERSONAL_DETAILS_FIRSTNAME_NULL = PersonalDetails.builder()
        .firstname(null)
        .lastname(VALID_LASTNAME)
        .phoneNumber(VALID_PHONE_NUMBER_INTERNATIONAL_FORMAT)
        .build();

    private static final PersonalDetails ERR_PERSONAL_DETAILS_LASTNAME_NULL = PersonalDetails.builder()
        .firstname(VALID_FIRSTNAME)
        .lastname(null)
        .phoneNumber(VALID_PHONE_NUMBER_INTERNATIONAL_FORMAT)
        .build();

    private static final PersonalDetails ERR_PERSONAL_DETAILS_PHONE_NUMBER_NULL = PersonalDetails.builder()
        .firstname(VALID_FIRSTNAME)
        .lastname(VALID_LASTNAME)
        .phoneNumber(null)
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
        void testPersonalDetailsValid() {
            Set<ConstraintViolation<PersonalDetails>> violations = validator.validate(VALID_PERSONAL_DETAILS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPersonalDetailsInvalidFirstnameNull() {
            Set<ConstraintViolation<PersonalDetails>> violations = validator.validate(ERR_PERSONAL_DETAILS_FIRSTNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_REQUIRED)));
        }

        @Test
        void testPersonalDetailsInvalidLastnameNull() {
            Set<ConstraintViolation<PersonalDetails>> violations = validator.validate(ERR_PERSONAL_DETAILS_LASTNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_REQUIRED)));
        }

        @Test
        void testPersonalDetailsInvalidPhoneNumberNull() {
            Set<ConstraintViolation<PersonalDetails>> violations = validator.validate(ERR_PERSONAL_DETAILS_PHONE_NUMBER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_REQUIRED)));
        }
    }
}
