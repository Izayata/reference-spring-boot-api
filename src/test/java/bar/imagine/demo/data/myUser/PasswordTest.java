package bar.imagine.demo.data.myUser;

import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_VALUE_REQUIRED;
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

public class PasswordTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Password ERR_PASSWORD_EMPTY = new Password("");
    private static final Password ERR_PASSWORD_NULL = new Password(null);
    private static final Password ERR_PASSWORD_SPACE_ONLY = new Password(" ");
    public static final Password VALID_PASSWORD = new Password("aA4!");


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
            Set<ConstraintViolation<Password>> violations = validator.validate(VALID_PASSWORD);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPasswordInvalidEmpty() {
            Set<ConstraintViolation<Password>> violations = validator.validate(ERR_PASSWORD_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_VALUE_REQUIRED)));
        }

        @Test
        void testPasswordInvalidNull() {
            Set<ConstraintViolation<Password>> violations = validator.validate(ERR_PASSWORD_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_VALUE_REQUIRED)));
        }

        @Test
        void testPasswordInvalidWhitespaceOnly() {
            Set<ConstraintViolation<Password>> violations = validator.validate(ERR_PASSWORD_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_VALUE_REQUIRED)));
        }
    }
}
