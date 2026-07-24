package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_MIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import bar.imagine.demo.data.customer.address.Street;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class StreetTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Street ERR_STREET_EMPTY = new Street("");
    private static final Street ERR_STREET_NULL = new Street(null);
    private static final Street ERR_STREET_SPACE_ONLY = new Street("  ");
    private static final Street ERR_STREET_TOO_LONG = new Street("a".repeat(STREET_MAX_LENGTH + 1));
    private static final Street ERR_STREET_TOO_SHORT = new Street("A");
    private static final Street ERR_STREET_LEADING_SPACES = new Street(" Besze János utca");
    private static final Street ERR_STREET_TRAILING_SPACES = new Street("Besze János utca ");
    private static final Street ERR_STREET_LEADING_TRAILING_SPACES = new Street(" Besze János utca ");
    private static final Street ERR_STREET_INVALID_CHARACTER = new Street("th@Avenue");
    public static final Street VALID_STREET = new Street("Besze János utca");
    private static final Street VALID_STREET_MIN_LENGTH = new Street("A".repeat(STREET_MIN_LENGTH));
    private static final Street VALID_STREET_MAX_LENGTH = new Street("A".repeat(STREET_MAX_LENGTH));

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
        void testStreetValidSimple() {
            Set<ConstraintViolation<Street>> violations = validator.validate(VALID_STREET);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetValidMinLength() {
            Set<ConstraintViolation<Street>> violations = validator.validate(VALID_STREET_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetValidMaxLength() {
            Set<ConstraintViolation<Street>> violations = validator.validate(VALID_STREET_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testStreetInvalidEmpty() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_LENGTH)));
        }

        @Test
        void testStreetInvalidNull() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_VALUE_REQUIRED)));
        }

        @Test
        void testStreetInvalidSpaceOnly() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetInvalidTooLong() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_LENGTH)));
        }

        @Test
        void testStreetInvalidTooShort() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_LENGTH)));
        }

        @Test
        void testStreetInvalidLeadingSpaces() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetInvalidTrailingSpaces() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetInvalidCharacters() {
            Set<ConstraintViolation<Street>> violations = validator.validate(ERR_STREET_INVALID_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }
    }
}
