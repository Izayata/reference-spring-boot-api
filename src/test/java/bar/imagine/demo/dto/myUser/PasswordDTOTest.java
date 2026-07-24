package bar.imagine.demo.dto.myUser;

import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_LENGTH;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_VALUE_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.PASSWORD_MAX_LENGTH;
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

public class PasswordDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final PasswordDTO ERR_PASSWORD_DTO_EMPTY = new PasswordDTO("");
    private static final PasswordDTO ERR_PASSWORD_DTO_NULL = new PasswordDTO(null);
    private static final PasswordDTO ERR_PASSWORD_DTO_SPACE_ONLY = new PasswordDTO("        ");
    private static final PasswordDTO ERR_PASSWORD_DTO_TOO_LONG = new PasswordDTO("A".repeat(PASSWORD_MAX_LENGTH + 1 - 3) + "b1!");
    private static final PasswordDTO ERR_PASSWORD_DTO_TOO_SHORT = new PasswordDTO("Ab1!");
    private static final PasswordDTO ERR_PASSWORD_DTO_LEADING_SPACES = new PasswordDTO(" ValidPass123!");
    private static final PasswordDTO ERR_PASSWORD_DTO_TRAILING_SPACES = new PasswordDTO("ValidPass123! ");
    private static final PasswordDTO ERR_PASSWORD_DTO_LEADING_TRAILING_SPACES = new PasswordDTO(" ValidPass123! ");
    private static final PasswordDTO ERR_PASSWORD_DTO_CONTAINS_SPACE = new PasswordDTO("Valid Pass123!");
    private static final PasswordDTO ERR_PASSWORD_DTO_NO_LOWERCASE_LETTER = new PasswordDTO("VALIDPASS123!");
    private static final PasswordDTO ERR_PASSWORD_DTO_NO_UPPERCASE_LETTER = new PasswordDTO("validpass123!");
    private static final PasswordDTO ERR_PASSWORD_DTO_NO_DIGIT = new PasswordDTO("ValidPass!");
    private static final PasswordDTO ERR_PASSWORD_DTO_NO_SPECIAL_CHARACTER = new PasswordDTO(" ValidPass123");
    public static final PasswordDTO VALID_PASSWORD_DTO = new PasswordDTO("ValidPass123!");
    private static final PasswordDTO VALID_PASSWORD_DTO_MIN_LENGTH = new PasswordDTO("Abc1234!");
    private static final PasswordDTO VALID_PASSWORD_DTO_MAX_LENGTH = new PasswordDTO("A".repeat(PASSWORD_MAX_LENGTH - 5) + "b123!");

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
        void testPasswordDtoValidSimple() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(VALID_PASSWORD_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testPasswordDtoValidMinLength() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(VALID_PASSWORD_DTO_MIN_LENGTH);
            violations.forEach(v -> System.err.println(v.getMessage()));
            assertTrue(violations.isEmpty());
        }

        @Test
        void testPasswordDtoValidMaxLength() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(VALID_PASSWORD_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPasswordDtoInvalidEmpty() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_LENGTH)));
        }

        @Test
        void testPasswordDtoInvalidNull() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_VALUE_REQUIRED)));
        }

        @Test
        void testPasswordDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidTooLong() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_LENGTH)));
        }

        @Test
        void testPasswordDtoInvalidTooShort() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_LENGTH)));
        }

        @Test
        void testPasswordDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidContainsSpace() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_CONTAINS_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidNoLowercaseLetter() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_NO_LOWERCASE_LETTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidNoUppercaseLetter() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_NO_UPPERCASE_LETTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidNoDigit() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_NO_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testPasswordDtoInvalidNoSpecialCharacter() {
            Set<ConstraintViolation<PasswordDTO>> violations = validator.validate(ERR_PASSWORD_DTO_NO_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)));
        }
    }
}
