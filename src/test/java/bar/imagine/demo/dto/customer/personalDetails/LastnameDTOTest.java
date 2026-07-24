package bar.imagine.demo.dto.customer.personalDetails;

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

public class LastnameDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final LastnameDTO ERR_LASTNAME_DTO_EMPTY = new LastnameDTO("");
    private static final LastnameDTO ERR_LASTNAME_DTO_NULL = new LastnameDTO(null);
    private static final LastnameDTO ERR_LASTNAME_DTO_SPACE_ONLY = new LastnameDTO("  ");
    private static final LastnameDTO ERR_LASTNAME_DTO_TOO_LONG = new LastnameDTO("A".repeat(FIRSTNAME_MAX_LENGTH + 1));
    private static final LastnameDTO ERR_LASTNAME_DTO_TOO_SHORT = new LastnameDTO("A");
    private static final LastnameDTO ERR_LASTNAME_DTO_INVALID_DIGIT = new LastnameDTO("Anna1");
    private static final LastnameDTO ERR_LASTNAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER = new LastnameDTO("Anna!");
    public static final LastnameDTO VALID_LASTNAME_DTO_LEADING_SPACES = new LastnameDTO(" Anna");
    public static final LastnameDTO VALID_LASTNAME_DTO_TRAILING_SPACES = new LastnameDTO("Anna ");
    public static final LastnameDTO VALID_LASTNAME_DTO_LEADING_TRAILING_SPACES = new LastnameDTO(" Anna ");
    public static final LastnameDTO VALID_LASTNAME_DTO = new LastnameDTO("Anna");
    private static final LastnameDTO VALID_LASTNAME_DTO_MIN_LENGTH = new LastnameDTO("An");
    private static final LastnameDTO VALID_LASTNAME_DTO_MAX_LENGTH = new LastnameDTO("A".repeat(FIRSTNAME_MAX_LENGTH));
    private static final LastnameDTO VALID_LASTNAME_DTO_HUN_LETTERS = new LastnameDTO("Áron Örs Éva");
    private static final LastnameDTO VALID_LASTNAME_DTO_DASH_APOSTROPHE_DOT = new LastnameDTO("Anna-Mari O'Neil St. Joe");

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
        void testLastnameDtoValidSimple() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameDtoValidMinLength() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameDtoValidMaxLength() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameDtoValidWithHungarianLetters() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testLastnameDtoValidWithDashApostropheDot() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_DASH_APOSTROPHE_DOT);
            assertTrue(violations.isEmpty());
        }


    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testLastnameDtoInvalidEmpty() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_LENGTH)));
        }

        @Test
        void testLastnameDtoInvalidNull() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_VALUE_REQUIRED)));
        }

        @Test
        void testLastnameDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameDtoInvalidTooLong() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_LENGTH)));
        }

        @Test
        void testLastnameDtoInvalidTooShort() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_LENGTH)));
        }

        @Test
        void testLastnameDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameDtoInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(VALID_LASTNAME_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameDtoInvalidCharacterDigit() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_INVALID_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testLastnameDtoInvalidCharacterBannedSpecialCharacter() {
            Set<ConstraintViolation<LastnameDTO>> violations = validator.validate(ERR_LASTNAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_INVALID_CHARACTERS)));
        }
    }
}
