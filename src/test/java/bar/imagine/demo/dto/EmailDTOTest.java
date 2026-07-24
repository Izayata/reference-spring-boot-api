package bar.imagine.demo.dto;

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

public class EmailDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final EmailDTO ERR_EMAIL_DTO_EMPTY = new EmailDTO("");
    private static final EmailDTO ERR_EMAIL_DTO_NULL = new EmailDTO(null);
    private static final EmailDTO ERR_EMAIL_DTO_SPACE_ONLY = new EmailDTO(" ");
    private static final EmailDTO ERR_EMAIL_DTO_TOO_LONG = new EmailDTO("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com");
    private static final EmailDTO ERR_EMAIL_DTO_INVALID_FORMAT_NO_AT_SYMBOL = new EmailDTO("testexample.com");
    private static final EmailDTO ERR_EMAIL_DTO_INVALID_FORMAT_NO_LOCAL = new EmailDTO("@example.com");
    private static final EmailDTO ERR_EMAIL_DTO_INVALID_FORMAT_NO_DOMAIN = new EmailDTO("test@.com");
    private static final EmailDTO ERR_EMAIL_DTO_INVALID_FORMAT_NO_TLD = new EmailDTO("test@example.");
    private static final EmailDTO ERR_EMAIL_DTO_LEADING_SPACES = new EmailDTO(" test@example.com");
    private static final EmailDTO ERR_EMAIL_DTO_TRAILING_SPACES = new EmailDTO("test@example.com ");
    private static final EmailDTO ERR_EMAIL_DTO_LEADING_TRAILING_SPACES = new EmailDTO(" test@example.com ");
    public static final EmailDTO VALID_EMAIL_DTO = new EmailDTO("test@example.com");
    private static final EmailDTO VALID_EMAIL_DTO_MAX_LENGTH = new EmailDTO("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@test.com");

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
        void testEmailDtoValidSimple() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(VALID_EMAIL_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testEmailDtoValidMaxLength() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(VALID_EMAIL_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testEmailDtoInvalidEmpty() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
        }

        @Test
        void testEmailDtoInvalidNull() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
        }

        @Test
        void testEmailDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidTooLong() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidNoAtSymbol() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_INVALID_FORMAT_NO_AT_SYMBOL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidNoLocal() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_INVALID_FORMAT_NO_LOCAL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidNoDomain() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_INVALID_FORMAT_NO_DOMAIN);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidNoTLD() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_INVALID_FORMAT_NO_TLD);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidLeadingSpaces() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidTrailingSpaces() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }

        @Test
        void testEmailDtoInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<EmailDTO>> violations = validator.validate(ERR_EMAIL_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_INVALID_FORMAT)));
        }
    }
}
