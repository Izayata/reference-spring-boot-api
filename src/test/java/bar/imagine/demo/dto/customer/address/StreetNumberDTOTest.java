package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.STREET_NUMBER_MAX_LENGTH;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import bar.imagine.demo.dto.customer.address.StreetNumberDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class StreetNumberDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_EMPTY = new StreetNumberDTO("");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_NULL = new StreetNumberDTO(null);
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_SPACE_ONLY = new StreetNumberDTO(" ");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_ZERO = new StreetNumberDTO("0");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_ZERO_SLASH_ZERO = new StreetNumberDTO("0/0");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_ZERO_SLASH_ONE = new StreetNumberDTO("0/1");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_ZERO_SLASH_UPPERCASE_A = new StreetNumberDTO("0/A");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_ZERO_SLASH_LOWERCASE_A = new StreetNumberDTO("0/a");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_TOO_LONG = new StreetNumberDTO("1".repeat(STREET_NUMBER_MAX_LENGTH + 1));
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_LEADING_SPACES = new StreetNumberDTO(" 21/A");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_TRAILING_SPACES = new StreetNumberDTO("21/A ");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_LEADING_TRAILING_SPACES = new StreetNumberDTO(" 21/A ");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_CONTAINS_SPACES = new StreetNumberDTO("2 1/A");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_INVALID_FORMAT = new StreetNumberDTO("21@A");
    private static final StreetNumberDTO ERR_STREET_DTO_NUMBER_INVALID_CHARACTER  = new StreetNumberDTO("1/a@");
    public static final StreetNumberDTO VALID_STREET_NUMBER_DTO = new StreetNumberDTO("21");
    public static final StreetNumberDTO VALID_STREET_NUMBER_DTO_SLASH_UPPERCASE_LETTER = new StreetNumberDTO("1/A");
    public static final StreetNumberDTO VALID_STREET_NUMBER_DTO_SLASH_LOWERCASE_LETTER = new StreetNumberDTO("21/a");
    private static final StreetNumberDTO VALID_STREET_NUMBER_DTO_UPPERCASE_LETTER = new StreetNumberDTO("21A");
    private static final StreetNumberDTO VALID_STREET_NUMBER_DTO_LOWERCASE_LETTER = new StreetNumberDTO("21a");
    private static final StreetNumberDTO VALID_STREET_NUMBER_DTO_SLASH_NUMBER = new StreetNumberDTO("2/2");
    private static final StreetNumberDTO VALID_STREET_NUMBER_DTO_MAX_LENGTH = new StreetNumberDTO("1".repeat(STREET_NUMBER_MAX_LENGTH));
    private static final StreetNumberDTO VALID_STREET_NUMBER_DTO_MIN_LENGTH = new StreetNumberDTO("1");

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
        void testStreetNumberDtoValidNumberOnly() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidNumberSlashUppercaseLetter() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_SLASH_UPPERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidNumberSlashLowercaseLetter() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_SLASH_LOWERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidUppercaseLetter() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_UPPERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidLowercaseLetter() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_LOWERCASE_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidNumberSlashNumber() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_SLASH_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidMinLength() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testStreetNumberDtoValidMaxLength() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(VALID_STREET_NUMBER_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testStreetNumberDtoInvalidEmpty() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_LENGTH)));
        }

        @Test
        void testStreetNumberDtoInvalidNull() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)));
        }

        @Test
        void testStreetNumberDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberDtoForbiddenZero() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_ZERO);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberDtoForbiddenZeroSlashZero() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_ZERO_SLASH_ZERO);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberDtoForbiddenZeroSlashOne() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_ZERO_SLASH_ONE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberDtoForbiddenZeroSlashUppercaseA() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_ZERO_SLASH_UPPERCASE_A);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberDtoForbiddenZeroSlashLowercaseA() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_ZERO_SLASH_LOWERCASE_A);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)));
        }

        @Test
        void testStreetNumberDtoInvalidTooLong() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_LENGTH)));
        }

        @Test
        void testStreetNumberDtoInvalidLeadingSpaces() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberDtoInvalidTrailingSpaces() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberDtoInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberDtoInvalidContainsSpaces() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberDtoInvalidFormat() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }

        @Test
        void testStreetNumberDtoInvalidCharacter() {
            Set<ConstraintViolation<StreetNumberDTO>> violations = validator.validate(ERR_STREET_DTO_NUMBER_INVALID_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_INVALID_FORMAT)));
        }
    }
}
