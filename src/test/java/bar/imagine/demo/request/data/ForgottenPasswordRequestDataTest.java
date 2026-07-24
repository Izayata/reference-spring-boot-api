package bar.imagine.demo.request.data;

import static bar.imagine.demo.dto.EmailDTOTest.VALID_EMAIL_DTO;
import static bar.imagine.demo.dto.myUser.MyUsernameDTOTest.VALID_MY_USERNAME_DTO;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_VALUE_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ForgottenPasswordRequestDataTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final ForgottenPasswordRequestData VALID_FORGOTTEN_PASSWORD_REQUEST_DATA = ForgottenPasswordRequestData.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(VALID_MY_USERNAME_DTO)
        .build();

    private static final ForgottenPasswordRequestData ERR_FORGOTTEN_PASSWORD_REQUEST_DATA_EMAIL_NULL = ForgottenPasswordRequestData.builder()
        .email(null)
        .myUsername(VALID_MY_USERNAME_DTO)
        .build();

    private static final ForgottenPasswordRequestData ERR_FORGOTTEN_PASSWORD_REQUEST_DATA_MY_USERNAME_NULL = ForgottenPasswordRequestData.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(null)
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

    @Test
    void testValidForgottenPasswordRequestData() {
        Set<ConstraintViolation<ForgottenPasswordRequestData>> violations = validator.validate(VALID_FORGOTTEN_PASSWORD_REQUEST_DATA);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidEmailNull() {
        Set<ConstraintViolation<ForgottenPasswordRequestData>> violations = validator.validate(ERR_FORGOTTEN_PASSWORD_REQUEST_DATA_EMAIL_NULL);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_VALUE_REQUIRED)));
    }

    @Test
    void testInvalidMyUsernameNull() {
        Set<ConstraintViolation<ForgottenPasswordRequestData>> violations = validator.validate(ERR_FORGOTTEN_PASSWORD_REQUEST_DATA_MY_USERNAME_NULL);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_REQUIRED)));
    }
}
