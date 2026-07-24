package bar.imagine.demo.dto.customer.personalDetails;

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

public class FirstnameDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_EMPTY = new FirstnameDTO("");
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_NULL = new FirstnameDTO(null);
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_SPACE_ONLY = new FirstnameDTO("  ");
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_TOO_LONG = new FirstnameDTO("A".repeat(FIRSTNAME_MAX_LENGTH + 1));
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_TOO_SHORT = new FirstnameDTO("A");
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_INVALID_DIGIT = new FirstnameDTO("Anna1");
    private static final FirstnameDTO ERR_FIRSTNAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER = new FirstnameDTO("Anna!");
    public static final FirstnameDTO VALID_FIRSTNAME_DTO_LEADING_SPACES = new FirstnameDTO(" Anna");
    public static final FirstnameDTO VALID_FIRSTNAME_DTO_TRAILING_SPACES = new FirstnameDTO("Anna ");
    public static final FirstnameDTO VALID_FIRSTNAME_DTO_LEADING_TRAILING_SPACES = new FirstnameDTO(" Anna ");
    public static final FirstnameDTO VALID_FIRSTNAME_DTO = new FirstnameDTO("Anna");
    private static final FirstnameDTO VALID_FIRSTNAME_DTO_MIN_LENGTH = new FirstnameDTO("An");
    private static final FirstnameDTO VALID_FIRSTNAME_DTO_MAX_LENGTH = new FirstnameDTO("A".repeat(FIRSTNAME_MAX_LENGTH));
    private static final FirstnameDTO VALID_FIRSTNAME_DTO_HUN_LETTERS = new FirstnameDTO("Áron Örs Éva");
    private static final FirstnameDTO VALID_FIRSTNAME_DTO_DASH_APOSTROPHE_DOT = new FirstnameDTO("Anna-Mari O'Neil St. Joe");

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
        void testFirstnameDtoValidSimple() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameDtoValidMinLength() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameDtoValidMaxLength() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameDtoValidWithHungarianLetters() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFirstnameDtoValidWithDashApostropheDot() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_DASH_APOSTROPHE_DOT);
            assertTrue(violations.isEmpty());
        }


    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testFirstnameDtoInvalidEmpty() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_LENGTH)));
        }

        @Test
        void testFirstnameDtoInvalidNull() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_VALUE_REQUIRED)));
        }

        @Test
        void testFirstnameDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameDtoInvalidTooLong() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_LENGTH)));
        }

        @Test
        void testFirstnameDtoInvalidTooShort() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_LENGTH)));
        }

        @Test
        void testFirstnameDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameDtoInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(VALID_FIRSTNAME_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameDtoInvalidCharacterDigit() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_INVALID_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }

        @Test
        void testFirstnameDtoInvalidCharacterBannedSpecialCharacter() {
            Set<ConstraintViolation<FirstnameDTO>> violations = validator.validate(ERR_FIRSTNAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)));
        }
    }
}
