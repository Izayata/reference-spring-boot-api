package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.STREET_NUMBER_MAX_LENGTH;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import bar.imagine.demo.data.customer.address.StreetNumber;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class StreetNumberTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final StreetNumber ERR_STREET_NUMBER_EMPTY = new StreetNumber("");
    private static final StreetNumber ERR_STREET_NUMBER_NULL = new StreetNumber(null);
    private static final StreetNumber ERR_STREET_NUMBER_SPACE_ONLY = new StreetNumber(" ");
    private static final StreetNumber ERR_STREET_NUMBER_ZERO = new StreetNumber("0");
    private static final StreetNumber ERR_STREET_NUMBER_ZERO_SLASH_ZERO = new StreetNumber("0/0");
    private static final StreetNumber ERR_STREET_NUMBER_ZERO_SLASH_ONE = new StreetNumber("0/1");
    private static final StreetNumber ERR_STREET_NUMBER_ZERO_SLASH_UPPERCASE_A = new StreetNumber("0/A");
    private static final StreetNumber ERR_STREET_NUMBER_ZERO_SLASH_LOWERCASE_A = new StreetNumber("0/a");
    private static final StreetNumber ERR_STREET_NUMBER_TOO_LONG = new StreetNumber("1".repeat(STREET_NUMBER_MAX_LENGTH + 1));
    private static final StreetNumber ERR_STREET_NUMBER_LEADING_SPACES = new StreetNumber(" 12/A");
    private static final StreetNumber ERR_STREET_NUMBER_TRAILING_SPACES = new StreetNumber("12/A ");
    private static final StreetNumber ERR_STREET_NUMBER_LEADING_TRAILING_SPACES = new StreetNumber(" 12/A ");
    private static final StreetNumber ERR_STREET_NUMBER_CONTAINS_SPACES = new StreetNumber("1 2/A");
    private static final StreetNumber ERR_STREET_NUMBER_INVALID_FORMAT = new StreetNumber("12@A");
    private static final StreetNumber ERR_STREET_NUMBER_INVALID_CHARACTER  = new StreetNumber("1/a@");
    public static final StreetNumber VALID_STREET_NUMBER = new StreetNumber("12");
    public static final StreetNumber VALID_STREET_NUMBER_SLASH_UPPERCASE_LETTER = new StreetNumber("12/A");
    public static final StreetNumber VALID_STREET_NUMBER_SLASH_LOWERCASE_LETTER = new StreetNumber("12/a");
    private static final StreetNumber VALID_STREET_NUMBER_UPPERCASE_LETTER = new StreetNumber("12A");
    private static final StreetNumber VALID_STREET_NUMBER_LOWERCASE_LETTER = new StreetNumber("12a");
    private static final StreetNumber VALID_STREET_NUMBER_SLASH_NUMBER = new StreetNumber("2/2");
    private static final StreetNumber VALID_STREET_NUMBER_MAX_LENGTH = new StreetNumber("1".repeat(STREET_NUMBER_MAX_LENGTH));
    private static final StreetNumber VALID_STREET_NUMBER_MIN_LENGTH = new StreetNumber("1");

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
        void testStreetNumberValidNumberOnly() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidNumberSlashUppercaseLetter() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_SLASH_UPPERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidNumberSlashLowercaseLetter() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_SLASH_LOWERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidUppercaseLetter() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_UPPERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidLowercaseLetter() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_LOWERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidNumberSlashNumber() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_SLASH_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidMinLength() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberValidMaxLength() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(VALID_STREET_NUMBER_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testStreetNumberInvalidEmpty() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_LENGTH)));
        }

        @Test
        void testStreetNumberInvalidNull() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)));
        }

        @Test
        void testStreetNumberInvalidSpaceOnly() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberForbiddenZero() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_ZERO);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberForbiddenZeroSlashZero() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_ZERO_SLASH_ZERO);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberForbiddenZeroSlashOne() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_ZERO_SLASH_ONE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberForbiddenZeroSlashUppercaseA() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_ZERO_SLASH_UPPERCASE_A);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberForbiddenZeroSlashLowercaseA() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_ZERO_SLASH_LOWERCASE_A);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberInvalidTooLong() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_LENGTH)));
        }

        @Test
        void testStreetNumberInvalidLeadingSpaces() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberInvalidTrailingSpaces() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberInvalidContainsSpaces() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberInvalidFormat() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberInvalidCharacter() {
            Set<ConstraintViolation<StreetNumber>> violations = validator.validate(ERR_STREET_NUMBER_INVALID_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }
    }
}
