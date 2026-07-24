package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_MIN_LENGTH;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import bar.imagine.demo.dto.customer.address.StreetDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class StreetDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final StreetDTO ERR_STREET_DTO_EMPTY = new StreetDTO("");
    private static final StreetDTO ERR_STREET_DTO_NULL = new StreetDTO(null);
    private static final StreetDTO ERR_STREET_DTO_SPACE_ONLY = new StreetDTO("  ");
    private static final StreetDTO ERR_STREET_DTO_TOO_LONG = new StreetDTO("a".repeat(STREET_MAX_LENGTH + 1));
    private static final StreetDTO ERR_STREET_DTO_TOO_SHORT = new StreetDTO("A");
    private static final StreetDTO ERR_STREET_DTO_LEADING_SPACES = new StreetDTO(" Kossuth Lajos utca");
    private static final StreetDTO ERR_STREET_DTO_TRAILING_SPACES = new StreetDTO("Kossuth Lajos utca ");
    private static final StreetDTO ERR_STREET_DTO_LEADING_TRAILING_SPACES = new StreetDTO(" Kossuth Lajos utca ");
    private static final StreetDTO ERR_STREET_DTO_INVALID_CHARACTER = new StreetDTO("@mbrózi@ utc@");
    public static final StreetDTO VALID_STREET_DTO = new StreetDTO("Kossuth Lajos utca");
    private static final StreetDTO VALID_STREET_DTO_MIN_LENGTH = new StreetDTO("A".repeat(STREET_MIN_LENGTH));
    private static final StreetDTO VALID_STREET_DTO_MAX_LENGTH = new StreetDTO("A".repeat(STREET_MAX_LENGTH));

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
        void testStreetDtoValidSimple() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(VALID_STREET_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetDtoValidMinLength() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(VALID_STREET_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetDtoValidMaxLength() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(VALID_STREET_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testStreetDtoInvalidEmpty() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_LENGTH)));
        }

        @Test
        void testStreetDtoInvalidNull() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_VALUE_REQUIRED)));
        }

        @Test
        void testStreetDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetDtoInvalidTooLong() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_LENGTH)));
        }

        @Test
        void testStreetDtoInvalidTooShort() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_LENGTH)));
        }

        @Test
        void testStreetDtoInvalidLeadingSpaces() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetDtoInvalidTrailingSpaces() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetDtoInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }

        @Test
        void testStreetDtoInvalidCharacters() {
            Set<ConstraintViolation<StreetDTO>> violations = validator.validate(ERR_STREET_DTO_INVALID_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_INVALID_CHARACTERS)));
        }
    }
}
