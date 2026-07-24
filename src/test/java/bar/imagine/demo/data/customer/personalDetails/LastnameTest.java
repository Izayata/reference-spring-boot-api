package bar.imagine.demo.data.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.FirstnameUtils.FIRSTNAME_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_LENGTH;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_VALUE_REQUIRED;
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

public class LastnameTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Lastname ERR_LASTNAME_EMPTY = new Lastname("");
    private static final Lastname ERR_LASTNAME_NULL = new Lastname(null);
    private static final Lastname ERR_LASTNAME_SPACE_ONLY = new Lastname("  ");
    private static final Lastname ERR_LASTNAME_TOO_LONG = new Lastname("A".repeat(FIRSTNAME_MAX_LENGTH + 1));
    private static final Lastname ERR_LASTNAME_TOO_SHORT = new Lastname("A");
    private static final Lastname ERR_LASTNAME_INVALID_DIGIT = new Lastname("Anna1");
    private static final Lastname ERR_LASTNAME_INVALID_BANNED_SPECIAL_CHARACTER = new Lastname("Anna!");
    public static final Lastname VALID_LASTNAME_LEADING_SPACES = new Lastname(" Anna");
    public static final Lastname VALID_LASTNAME_TRAILING_SPACES = new Lastname("Anna ");
    public static final Lastname VALID_LASTNAME_LEADING_TRAILING_SPACES = new Lastname(" Anna ");
    public static final Lastname VALID_LASTNAME = new Lastname("Anna");
    private static final Lastname VALID_LASTNAME_MIN_LENGTH = new Lastname("An");
    private static final Lastname VALID_LASTNAME_MAX_LENGTH = new Lastname("A".repeat(FIRSTNAME_MAX_LENGTH));
    private static final Lastname VALID_LASTNAME_HUN_LETTERS = new Lastname("Áron Örs Éva");
    private static final Lastname VALID_LASTNAME_DASH_APOSTROPHE_DOT = new Lastname("Anna-Mari O'Neil St. Joe");

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
        void testLastnameValidSimple() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameValidMinLength() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameValidMaxLength() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameValidWithHungarianLetters() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameValidWithDashApostropheDot() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_DASH_APOSTROPHE_DOT);
            assertTrue(violations.isEmpty());
        }


    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testLastnameInvalidEmpty() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_LENGTH)));
        }

        @Test
        void testLastnameInvalidNull() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_VALUE_REQUIRED)));
        }

        @Test
        void testLastnameInvalidSpaceOnly() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameInvalidTooLong() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_LENGTH)));
        }

        @Test
        void testLastnameInvalidTooShort() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_LENGTH)));
        }

        @Test
        void testLastnameInvalidLeadingSpace() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameInvalidTrailingSpace() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(VALID_LASTNAME_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameInvalidCharacterDigit() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_INVALID_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameInvalidCharacterBannedSpecialCharacter() {
            Set<ConstraintViolation<Lastname>> violations = validator.validate(ERR_LASTNAME_INVALID_BANNED_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }
    }
}
