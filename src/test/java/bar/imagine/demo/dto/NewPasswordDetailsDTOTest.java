package bar.imagine.demo.dto;

import static bar.imagine.demo.dto.myUser.PasswordDTOTest.VALID_PASSWORD_DTO;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CONFIRM_NEW_PASSWORD_DO_NOT_MATCH_NEW_PASSWORD;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CONFIRM_NEW_PASSWORD_REQUIRED;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_NEW_PASSWORD_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import bar.imagine.demo.dto.myUser.PasswordDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class NewPasswordDetailsDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final NewPasswordDetailsDTO ERR_NEW_PASSWORD_WRAPPER_DTO_NEW_PASSWORD_NULL = NewPasswordDetailsDTO.builder()
        .newPassword(null)
        .confirmNewPassword(VALID_PASSWORD_DTO)
        .build();

    private static final NewPasswordDetailsDTO ERR_NEW_PASSWORD_WRAPPER_DTO_NEW_PASSWORD_MISSING = NewPasswordDetailsDTO.builder()
        .confirmNewPassword(VALID_PASSWORD_DTO)
        .build();

    private static final NewPasswordDetailsDTO ERR_NEW_PASSWORD_WRAPPER_DTO_CONFIRM_PASSWORD_NULL = NewPasswordDetailsDTO.builder()
        .newPassword(VALID_PASSWORD_DTO)
        .confirmNewPassword(null)
        .build();

    private static final NewPasswordDetailsDTO ERR_NEW_PASSWORD_WRAPPER_DTO_CONFIRM_PASSWORD_MISSING = NewPasswordDetailsDTO.builder()
        .newPassword(VALID_PASSWORD_DTO)
        .build();

    private static final NewPasswordDetailsDTO ERR_NEW_PASSWORD_WRAPPER_DTO_PASSWORDS_NOT_MATCHING = NewPasswordDetailsDTO.builder()
        .newPassword(VALID_PASSWORD_DTO)
        .confirmNewPassword(new PasswordDTO("differentPassword1!"))
        .build();

    public static final NewPasswordDetailsDTO VALID_NEW_PASSWORD_WRAPPER_DTO = NewPasswordDetailsDTO.builder()
        .newPassword(VALID_PASSWORD_DTO)
        .confirmNewPassword(VALID_PASSWORD_DTO)
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
        void testNewPasswordWrapperDtoValid() {
            Set<ConstraintViolation<NewPasswordDetailsDTO>> violations = validator.validate(VALID_NEW_PASSWORD_WRAPPER_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testNewPasswordWrapperDtoInvalidNewPasswordNull() {
            Set<ConstraintViolation<NewPasswordDetailsDTO>> violations = validator.validate(ERR_NEW_PASSWORD_WRAPPER_DTO_NEW_PASSWORD_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_NEW_PASSWORD_REQUIRED)));
        }

        @Test
        void testNewPasswordWrapperDtoInvalidNewPasswordMissing() {
            Set<ConstraintViolation<NewPasswordDetailsDTO>> violations = validator.validate(ERR_NEW_PASSWORD_WRAPPER_DTO_NEW_PASSWORD_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_NEW_PASSWORD_REQUIRED)));
        }

        @Test
        void testNewPasswordWrapperDtoInvalidConfirmPasswordNull() {
            Set<ConstraintViolation<NewPasswordDetailsDTO>> violations = validator.validate(ERR_NEW_PASSWORD_WRAPPER_DTO_CONFIRM_PASSWORD_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CONFIRM_NEW_PASSWORD_REQUIRED)));
        }

        @Test
        void testNewPasswordWrapperDtoInvalidConfirmPasswordMissing() {
            Set<ConstraintViolation<NewPasswordDetailsDTO>> violations = validator.validate(ERR_NEW_PASSWORD_WRAPPER_DTO_CONFIRM_PASSWORD_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CONFIRM_NEW_PASSWORD_REQUIRED)));
        }

        @Test
        void testNewPasswordWrapperDtoInvalidPasswordsNotMatching() {
            Set<ConstraintViolation<NewPasswordDetailsDTO>> violations = validator.validate(ERR_NEW_PASSWORD_WRAPPER_DTO_PASSWORDS_NOT_MATCHING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CONFIRM_NEW_PASSWORD_DO_NOT_MATCH_NEW_PASSWORD)));
        }
    }
}
