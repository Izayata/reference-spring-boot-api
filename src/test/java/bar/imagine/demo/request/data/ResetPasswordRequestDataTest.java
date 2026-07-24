package bar.imagine.demo.request.data;

import static bar.imagine.demo.dto.NewPasswordDetailsDTOTest.VALID_NEW_PASSWORD_WRAPPER_DTO;
import static bar.imagine.demo.util.NewPasswordDetailsUtils.ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED;
import static bar.imagine.demo.util.request.data.ResetPasswordRequestDataUtils.ERR_MSG_TOKEN_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ResetPasswordRequestDataTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final ResetPasswordRequestData VALID_RESET_PASSWORD_REQUEST_DATA = ResetPasswordRequestData.builder()
        .token("validToken123")
        .newPasswordDetails(VALID_NEW_PASSWORD_WRAPPER_DTO)
        .build();

    private static final ResetPasswordRequestData ERR_RESET_PASSWORD_REQUEST_DATA_TOKEN_NULL = ResetPasswordRequestData.builder()
        .token(null)
        .newPasswordDetails(VALID_NEW_PASSWORD_WRAPPER_DTO)
        .build();

    private static final ResetPasswordRequestData ERR_RESET_PASSWORD_REQUEST_DATA_TOKEN_BLANK = ResetPasswordRequestData.builder()
        .token("")
        .newPasswordDetails(VALID_NEW_PASSWORD_WRAPPER_DTO)
        .build();

    private static final ResetPasswordRequestData ERR_RESET_PASSWORD_REQUEST_DATA_NEW_PASSWORD_DETAILS_NULL = ResetPasswordRequestData.builder()
        .token("validToken123")
        .newPasswordDetails(null)
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
    void testValidResetPasswordRequestData() {
        Set<ConstraintViolation<ResetPasswordRequestData>> violations = validator.validate(VALID_RESET_PASSWORD_REQUEST_DATA);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidTokenNull() {
        Set<ConstraintViolation<ResetPasswordRequestData>> violations = validator.validate(ERR_RESET_PASSWORD_REQUEST_DATA_TOKEN_NULL);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_TOKEN_REQUIRED)));
    }

    @Test
    void testInvalidTokenBlank() {
        Set<ConstraintViolation<ResetPasswordRequestData>> violations = validator.validate(ERR_RESET_PASSWORD_REQUEST_DATA_TOKEN_BLANK);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_TOKEN_REQUIRED)));
    }

    @Test
    void testInvalidNewPasswordDetailsNull() {
        Set<ConstraintViolation<ResetPasswordRequestData>> violations = validator.validate(ERR_RESET_PASSWORD_REQUEST_DATA_NEW_PASSWORD_DETAILS_NULL);
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED)));
    }
}
