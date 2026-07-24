package bar.imagine.demo.data.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_LENGTH;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.FIRSTNAME_MAX_LENGTH;
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

public class FirstnameTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Firstname ERR_FIRSTNAME_EMPTY = new Firstname("");
    private static final Firstname ERR_FIRSTNAME_NULL = new Firstname(null);
    private static final Firstname ERR_FIRSTNAME_SPACE_ONLY = new Firstname("  ");
    private static final Firstname ERR_FIRSTNAME_TOO_LONG = new Firstname("A".repeat(FIRSTNAME_MAX_LENGTH + 1));
    private static final Firstname ERR_FIRSTNAME_TOO_SHORT = new Firstname("A");
    private static final Firstname ERR_FIRSTNAME_INVALID_DIGIT = new Firstname("Anna1");
    private static final Firstname ERR_FIRSTNAME_INVALID_BANNED_SPECIAL_CHARACTER = new Firstname("Anna!");
    public static final Firstname VALID_FIRSTNAME_LEADING_SPACES = new Firstname(" Anna");
    public static final Firstname VALID_FIRSTNAME_TRAILING_SPACES = new Firstname("Anna ");
    public static final Firstname VALID_FIRSTNAME_LEADING_TRAILING_SPACES = new Firstname(" Anna ");
    public static final Firstname VALID_FIRSTNAME = new Firstname("Anna");
    private static final Firstname VALID_FIRSTNAME_MIN_LENGTH = new Firstname("An");
    private static final Firstname VALID_FIRSTNAME_MAX_LENGTH = new Firstname("A".repeat(FIRSTNAME_MAX_LENGTH));
    private static final Firstname VALID_FIRSTNAME_HUN_LETTERS = new Firstname("Áron Örs Éva");
    private static final Firstname VALID_FIRSTNAME_DASH_APOSTROPHE_DOT = new Firstname("Anna-Mari O'Neil St. Joe");

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
        void testFirstnameValidSimple() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameValidMinLength() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameValidMaxLength() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameValidWithHungarianLetters() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameValidWithDashApostropheDot() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_DASH_APOSTROPHE_DOT);
            assertTrue(violations.isEmpty());
        }


    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testFirstnameInvalidEmpty() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_LENGTH)));
        }

        @Test
        void testFirstnameInvalidNull() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_VALUE_REQUIRED)));
        }

        @Test
        void testFirstnameInvalidSpaceOnly() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameInvalidTooLong() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_LENGTH)));
        }

        @Test
        void testFirstnameInvalidTooShort() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_LENGTH)));
        }

        @Test
        void testFirstnameInvalidLeadingSpace() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameInvalidTrailingSpace() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(VALID_FIRSTNAME_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameInvalidCharacterDigit() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_INVALID_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameInvalidCharacterBannedSpecialCharacter() {
            Set<ConstraintViolation<Firstname>> violations = validator.validate(ERR_FIRSTNAME_INVALID_BANNED_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }
    }
}
