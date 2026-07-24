package bar.imagine.demo.data;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_INVALID_FORMAT;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_VALUE_REQUIRED;
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

public class EmailTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Email ERR_EMAIL_EMPTY = new Email("");
    private static final Email ERR_EMAIL_NULL = new Email(null);
    private static final Email ERR_EMAIL_SPACE_ONLY = new Email(" ");
    private static final Email ERR_EMAIL_TOO_LONG = new Email("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com");
    private static final Email ERR_EMAIL_INVALID_FORMAT_NO_AT_SYMBOL = new Email("testexample.com");
    private static final Email ERR_EMAIL_INVALID_FORMAT_NO_LOCAL = new Email("@example.com");
    private static final Email ERR_EMAIL_INVALID_FORMAT_NO_DOMAIN = new Email("test@.com");
    private static final Email ERR_EMAIL_INVALID_FORMAT_NO_TLD = new Email("test@example.");
    private static final Email ERR_EMAIL_LEADING_SPACES = new Email(" test@example.com");
    private static final Email ERR_EMAIL_TRAILING_SPACES = new Email("test@example.com ");
    private static final Email ERR_EMAIL_LEADING_TRAILING_SPACES = new Email(" test@example.com ");
    public static final Email VALID_EMAIL = new Email("test@example.com");
    private static final Email VALID_EMAIL_MAX_LENGTH = new Email("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com");

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
        void testEmailValidSimple() {
            Set<ConstraintViolation<Email>> violations = validator.validate(VALID_EMAIL);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testEmailValidMaxLength() {
            Set<ConstraintViolation<Email>> violations = validator.validate(VALID_EMAIL_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testEmailInvalidEmpty() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
        }

        @Test
        void testEmailInvalidNull() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
        }

        @Test
        void testEmailInvalidSpaceOnly() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidTooLong() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidNoAtSymbol() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_INVALID_FORMAT_NO_AT_SYMBOL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidNoLocal() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_INVALID_FORMAT_NO_LOCAL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidNoDomain() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_INVALID_FORMAT_NO_DOMAIN);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidNoTLD() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_INVALID_FORMAT_NO_TLD);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidLeadingSpaces() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidTrailingSpaces() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<Email>> violations = validator.validate(ERR_EMAIL_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }
    }
}
