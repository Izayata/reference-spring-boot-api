package bar.imagine.demo.dto.myUser;

import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_LENGTH;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_VALUE_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.USERNAME_MAX_LENGTH;
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

public class MyUsernameDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_EMPTY = new MyUsernameDTO("");
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_NULL = new MyUsernameDTO(null);
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_WHITESPACE_ONLY = new MyUsernameDTO("   ");
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_TOO_LONG = new MyUsernameDTO("testTestTestTestTestT");
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_TOO_SHORT = new MyUsernameDTO("te");
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_CONTAINS_SPECIAL_CHARACTER = new MyUsernameDTO("User@");
    public static final MyUsernameDTO ERR_MY_USERNAME_DTO_LEADING_SPACES = new MyUsernameDTO(" ValidUser123");
    public static final MyUsernameDTO ERR_MY_USERNAME_DTO_TRAILING_SPACES = new MyUsernameDTO("ValidUser123 ");
    public static final MyUsernameDTO ERR_MY_USERNAME_DTO_LEADING_TRAILING_SPACES = new MyUsernameDTO(" ValidUser123 ");
    private static final MyUsernameDTO ERR_MY_USERNAME_DTO_CONTAINS_SPACE = new MyUsernameDTO("User Name");
    public static final MyUsernameDTO VALID_MY_USERNAME_DTO = new MyUsernameDTO("ValidUser123");
    private static final MyUsernameDTO VALID_MY_USERNAME_DTO_MIN_LENGTH = new MyUsernameDTO("Usr");
    private static final MyUsernameDTO VALID_MY_USERNAME_DTO_MAX_LENGTH = new MyUsernameDTO("U".repeat(USERNAME_MAX_LENGTH));

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
        void testUsernameDtoValidSimple() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(VALID_MY_USERNAME_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testUsernameDtoValidMinLength() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(VALID_MY_USERNAME_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testUsernameDtoValidMaxLength() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(VALID_MY_USERNAME_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testUsernameDtoInvalidEmpty() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_LENGTH)));
        }

        @Test
        void testUsernameDtoInvalidNull() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_VALUE_REQUIRED)));
        }

        @Test
        void testUsernameDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_WHITESPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameDtoInvalidTooLong() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_LENGTH)));
        }

        @Test
        void testUsernameDtoInvalidTooShort() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_LENGTH)));
        }

        @Test
        void testUsernameDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameDtoInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameDtoInvalidContainsSpace() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_CONTAINS_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameDtoInvalidContainsSpecialCharacter() {
            Set<ConstraintViolation<MyUsernameDTO>> violations = validator.validate(ERR_MY_USERNAME_DTO_CONTAINS_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }
    }
}
