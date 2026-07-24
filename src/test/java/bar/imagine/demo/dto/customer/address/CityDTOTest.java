package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_MIN_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_VALUE_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import bar.imagine.demo.dto.customer.address.CityDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class CityDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final CityDTO ERR_CITY_DTO_EMPTY = new CityDTO("");
    private static final CityDTO ERR_CITY_DTO_NULL = new CityDTO(null);
    private static final CityDTO ERR_CITY_DTO_SPACE_ONLY = new CityDTO("  ");
    private static final CityDTO ERR_CITY_DTO_TOO_LONG = new CityDTO("a".repeat(CITY_MAX_LENGTH + 1));
    private static final CityDTO ERR_CITY_DTO_TOO_SHORT = new CityDTO("N");
    private static final CityDTO ERR_CITY_DTO_LEADING_SPACE = new CityDTO(" Debrecen");
    private static final CityDTO ERR_CITY_DTO_TRAILING_SPACE = new CityDTO("Debrecen ");
    private static final CityDTO ERR_CITY_DTO_LEADING_TRAILING_SPACE = new CityDTO(" Debrecen ");
    private static final CityDTO ERR_CITY_DTO_INVALID_SPECIAL_CHARACTER = new CityDTO("Bud@pest");
    private static final CityDTO ERR_CITY_DTO_INVALID_LETTER = new CityDTO("Bud@pest");
    public static final CityDTO VALID_CITY_DTO_ASCII_ONLY = new CityDTO("Debrecen");
    private static final CityDTO VALID_CITY_DTO_MIN_LENGTH = new CityDTO("A".repeat(CITY_MIN_LENGTH));
    private static final CityDTO VALID_CITY_DTO_MAX_LENGTH = new CityDTO("A".repeat(CITY_MAX_LENGTH));
    private static final CityDTO VALID_CITY_DTO_HUN_LETTERS = new CityDTO("GyőrűűőŐÁÉ");

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
        void testCityDtoValid() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(VALID_CITY_DTO_ASCII_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testCityDtoValidMinLength() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(VALID_CITY_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testCityDtoValidMaxLength() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(VALID_CITY_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testCityDtoValidWithHungarianCharacters() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(VALID_CITY_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testCityDtoInvalidEmpty() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_LENGTH)));
        }

        @Test
        void testCityDtoInvalidNull() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_VALUE_REQUIRED)));
        }

        @Test
        void testCityDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityDtoInvalidTooLong() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_LENGTH)));
        }

        @Test
        void testCityDtoInvalidTooShort() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_LENGTH)));
        }

        @Test
        void testCityDtoInvalidLeadingSpaces() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_LEADING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityDtoInvalidTrailingSpaces() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_TRAILING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityDtoInvalidLeadingTrailing() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_LEADING_TRAILING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityDtoInvalidSpecialCharacter() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_INVALID_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }

        @Test
        void testCityDtoInvalidLetter() {
            Set<ConstraintViolation<CityDTO>> violations = validator.validate(ERR_CITY_DTO_INVALID_LETTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_INVALID_CHARACTERS)));
        }
    }
}
