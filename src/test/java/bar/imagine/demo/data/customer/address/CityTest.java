package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_MIN_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_VALUE_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import bar.imagine.demo.data.customer.address.City;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class CityTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final City ERR_CITY_EMPTY = new City("");
    private static final City ERR_CITY_NULL = new City(null);
    private static final City ERR_CITY_SPACE_ONLY = new City("  ");
    private static final City ERR_CITY_TOO_LONG = new City("a".repeat(CITY_MAX_LENGTH + 1));
    private static final City ERR_CITY_TOO_SHORT = new City("N");
    private static final City ERR_CITY_LEADING_SPACE = new City(" Debrecen");
    private static final City ERR_CITY_TRAILING_SPACE = new City("Debrecen ");
    private static final City ERR_CITY_LEADING_TRAILING_SPACE = new City(" Debrecen ");
    private static final City ERR_CITY_INVALID_SPECIAL_CHARACTER = new City("New@York");
    private static final City ERR_CITY_INVALID_LETTER = new City("New@York");
    public static final City VALID_CITY_ASCII_ONLY = new City("Debrecen");
    private static final City VALID_CITY_MIN_LENGTH = new City("A".repeat(CITY_MIN_LENGTH));
    private static final City VALID_CITY_MAX_LENGTH = new City("A".repeat(CITY_MAX_LENGTH));
    private static final City VALID_CITY_HUN_LETTERS = new City("GyőrűűőŐÁÉ");

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
        void testCityValid() {
            Set<ConstraintViolation<City>> violations = validator.validate(VALID_CITY_ASCII_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testCityValidMinLength() {
            Set<ConstraintViolation<City>> violations = validator.validate(VALID_CITY_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testCityValidMaxLength() {
            Set<ConstraintViolation<City>> violations = validator.validate(VALID_CITY_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testCityValidWithHungarianCharacters() {
            Set<ConstraintViolation<City>> violations = validator.validate(VALID_CITY_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testCityInvalidEmpty() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_LENGTH)));
        }

        @Test
        void testCityInvalidNull() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_VALUE_REQUIRED)));
        }

        @Test
        void testCityInvalidSpaceOnly() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityInvalidTooLong() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_LENGTH)));
        }

        @Test
        void testCityInvalidTooShort() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_LENGTH)));
        }

        @Test
        void testCityInvalidLeadingSpaces() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_LEADING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityInvalidTrailingSpaces() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_TRAILING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityInvalidLeadingTrailing() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_LEADING_TRAILING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityInvalidSpecialCharacter() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_INVALID_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityInvalidLetter() {
            Set<ConstraintViolation<City>> violations = validator.validate(ERR_CITY_INVALID_LETTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }
    }
}
