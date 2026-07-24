package bar.imagine.demo.data.myUser;

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

public class MyUsernameTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final MyUsername ERR_MY_USERNAME_EMPTY = new MyUsername("");
    private static final MyUsername ERR_MY_USERNAME_NULL = new MyUsername(null);
    private static final MyUsername ERR_MY_USERNAME_WHITESPACE_ONLY = new MyUsername("   ");
    private static final MyUsername ERR_MY_USERNAME_TOO_LONG = new MyUsername("testTestTestTestTestT");
    private static final MyUsername ERR_MY_USERNAME_TOO_SHORT = new MyUsername("te");
    private static final MyUsername ERR_MY_USERNAME_CONTAINS_SPECIAL_CHARACTER = new MyUsername("User@");
    public static final MyUsername ERR_MY_USERNAME_LEADING_SPACES = new MyUsername(" ValidUser123");
    public static final MyUsername ERR_MY_USERNAME_TRAILING_SPACES = new MyUsername("ValidUser123 ");
    public static final MyUsername ERR_MY_USERNAME_LEADING_TRAILING_SPACES = new MyUsername(" ValidUser123 ");
    private static final MyUsername ERR_MY_USERNAME_CONTAINS_SPACE = new MyUsername("User Name");
    public static final MyUsername VALID_MY_USERNAME = new MyUsername("ValidUser123");
    private static final MyUsername VALID_MY_USERNAME_MIN_LENGTH = new MyUsername("Usr");
    private static final MyUsername VALID_MY_USERNAME_MAX_LENGTH = new MyUsername("U".repeat(USERNAME_MAX_LENGTH));

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
        void testUsernameValidSimple() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(VALID_MY_USERNAME);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testUsernameValidMinLength() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(VALID_MY_USERNAME_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testUsernameValidMaxLength() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(VALID_MY_USERNAME_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testUsernameInvalidEmpty() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_LENGTH)));
        }

        @Test
        void testUsernameInvalidNull() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_VALUE_REQUIRED)));
        }

        @Test
        void testUsernameInvalidSpaceOnly() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_WHITESPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameInvalidTooLong() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_LENGTH)));
        }

        @Test
        void testUsernameInvalidTooShort() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_LENGTH)));
        }

        @Test
        void testUsernameInvalidLeadingSpace() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameInvalidTrailingSpace() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameInvalidContainsSpace() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_CONTAINS_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }

        @Test
        void testUsernameInvalidContainsSpecialCharacter() {
            Set<ConstraintViolation<MyUsername>> violations = validator.validate(ERR_MY_USERNAME_CONTAINS_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)));
        }
    }
}
