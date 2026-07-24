package bar.imagine.demo.data.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_VALUE_INVALID;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED;
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

public class PhoneNumberTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final PhoneNumber ERR_PHONE_NUMBER_EMPTY = new PhoneNumber("");
    private static final PhoneNumber ERR_PHONE_NUMBER_NULL = new PhoneNumber(null);
    private static final PhoneNumber ERR_PHONE_NUMBER_SPACE_ONLY = new PhoneNumber(" ");
    private static final PhoneNumber ERR_PHONE_NUMBER_LEADING_SPACES = new PhoneNumber(" +36703129085");
    private static final PhoneNumber ERR_PHONE_NUMBER_TRAILING_SPACES = new PhoneNumber("+36703129085 ");
    private static final PhoneNumber ERR_PHONE_NUMBER_LEADING_TRAILING_SPACES = new PhoneNumber(" +36703129085 ");
    private static final PhoneNumber ERR_PHONE_NUMBER_CONTAINS_SPACES = new PhoneNumber("+3670 3129085");
    private static final PhoneNumber ERR_PHONE_NUMBER_NO_PLUS_SIGN = new PhoneNumber("36703129085");
    private static final PhoneNumber ERR_PHONE_NUMBER_INVALID_FORMAT = new PhoneNumber("+123-abc-7890");
    public static final PhoneNumber VALID_PHONE_NUMBER_INTERNATIONAL_FORMAT = new PhoneNumber("+36703129085");
    public static final PhoneNumber VALID_PHONE_NUMBER_INTRA_FORMAT_HU = new PhoneNumber("06703129085");

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
        void testPhoneNumberValidInternationalFormat() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(VALID_PHONE_NUMBER_INTERNATIONAL_FORMAT);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testPhoneNumberValidIntraFormatHU() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(VALID_PHONE_NUMBER_INTRA_FORMAT_HU);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testPhoneNumberValidNoPlusSign() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_NO_PLUS_SIGN);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPhoneNumberInvalidEmpty() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_INVALID)));
        }

        @Test
        void testPhoneNumberInvalidNull() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)));
            // @ValidPhoneNumber returns true for null (null-safe by design), so VALUE_INVALID is not produced
        }

        @Test
        void testPhoneNumberInvalidSpaceOnly() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_INVALID)));
        }

        @Test
        void testPhoneNumberInvalidLeadingSpaces() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberInvalidTrailingSpaces() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberInvalidContainsSpaces() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberInvalidFormat() {
            Set<ConstraintViolation<PhoneNumber>> violations = validator.validate(ERR_PHONE_NUMBER_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_INVALID)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }
    }
}
