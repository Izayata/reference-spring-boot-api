package bar.imagine.demo.data;

import static bar.imagine.demo.data.CustomerTest.VALID_CUSTOMER;
import static bar.imagine.demo.data.EmailTest.VALID_EMAIL;
import static bar.imagine.demo.data.myUser.MyUsernameTest.VALID_MY_USERNAME;
import static bar.imagine.demo.data.myUser.PasswordTest.VALID_PASSWORD;
import static bar.imagine.demo.util.MyUserUtils.ERR_MSG_CUSTOMER_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_REQUIRED;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
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

class MyUserTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final MyUser ERR_MY_USER_EMAIL_NULL = MyUser.builder()
        .email(null)
        .myUsername(VALID_MY_USERNAME)
        .password(VALID_PASSWORD)
        .customer(VALID_CUSTOMER)
        .build();
    private static final MyUser ERR_MY_USER_EMAIL_MISSING = MyUser.builder()
        .myUsername(VALID_MY_USERNAME)
        .password(VALID_PASSWORD)
        .customer(VALID_CUSTOMER)
        .build();
    private static final MyUser ERR_MY_USER_USERNAME_NULL = MyUser.builder()
        .email(VALID_EMAIL)
        .myUsername(null)
        .password(VALID_PASSWORD)
        .customer(VALID_CUSTOMER)
        .build();
    private static final MyUser ERR_MY_USER_USERNAME_MISSING = MyUser.builder()
        .email(VALID_EMAIL)
        .password(VALID_PASSWORD)
        .customer(VALID_CUSTOMER)
        .build();
    private static final MyUser ERR_MY_USER_PASSWORD_NULL = MyUser.builder()
        .email(VALID_EMAIL)
        .myUsername(VALID_MY_USERNAME)
        .password(null)
        .customer(VALID_CUSTOMER)
        .build();
    private static final MyUser ERR_MY_USER_PASSWORD_MISSING = MyUser.builder()
        .email(VALID_EMAIL)
        .myUsername(VALID_MY_USERNAME)
        .customer(VALID_CUSTOMER)
        .build();
    private static final MyUser ERR_MY_USER_CUSTOMER_NULL = MyUser.builder()
        .email(VALID_EMAIL)
        .myUsername(VALID_MY_USERNAME)
        .password(VALID_PASSWORD)
        .customer(null)
        .build();
    private static final MyUser ERR_MY_USER_CUSTOMER_MISSING = MyUser.builder()
        .email(VALID_EMAIL)
        .myUsername(VALID_MY_USERNAME)
        .password(VALID_PASSWORD)
        .build();
    private static final MyUser VALID_MY_USER = MyUser.builder()
        .email(VALID_EMAIL)
        .myUsername(VALID_MY_USERNAME)
        .password(VALID_PASSWORD)
        .customer(VALID_CUSTOMER)
        .build();

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
        void testMyUserValid() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(VALID_MY_USER);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testMyUserInvalidEmailNull() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_EMAIL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testMyUserInvalidEmailMissing() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_EMAIL_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testMyUserInvalidUsernameNull() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_USERNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_REQUIRED)));
        }

        @Test
        void testMyUserInvalidUsernameMissing() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_USERNAME_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_REQUIRED)));
        }

        @Test
        void testMyUserInvalidPasswordNull() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_PASSWORD_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_REQUIRED)));
        }

        @Test
        void testMyUserInvalidPasswordMissing() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_PASSWORD_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PASSWORD_REQUIRED)));
        }

        @Test
        void testMyUserInvalidCustomerNull() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_CUSTOMER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CUSTOMER_REQUIRED)));
        }

        @Test
        void testMyUserInvalidCustomerMissingNull() {
            Set<ConstraintViolation<MyUser>> violations = validator.validate(ERR_MY_USER_CUSTOMER_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CUSTOMER_REQUIRED)));
        }
    }
}
