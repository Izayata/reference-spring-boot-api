package bar.imagine.demo.dto;

import static bar.imagine.demo.dto.myUser.PasswordDTOTest.VALID_PASSWORD_DTO;
import static bar.imagine.demo.util.NewPasswordDetailsUtils.ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CURRENT_PASSWORD_REQUIRED;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_NEW_PASSWORD_MATCHES_CURRENT_PASSWORD;
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

class PasswordChangeDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final NewPasswordDetailsDTO VALID_NEW_PASSWORD_WRAPPER = NewPasswordDetailsDTO.builder()
        .newPassword(new PasswordDTO("newPassword1!"))
        .confirmNewPassword(new PasswordDTO("newPassword1!"))
        .build();

    private static final PasswordChangeDTO VALID_PASSWORD_CHANGE_DTO = PasswordChangeDTO.builder()
        .currentPassword(VALID_PASSWORD_DTO)
        .newPasswordDetails(VALID_NEW_PASSWORD_WRAPPER)
        .build();

    private static final PasswordChangeDTO ERR_PASSWORD_CHANGE_DTO_CURRENT_PASSWORD_NULL = PasswordChangeDTO.builder()
        .currentPassword(null)
        .newPasswordDetails(VALID_NEW_PASSWORD_WRAPPER)
        .build();

    private static final PasswordChangeDTO ERR_PASSWORD_CHANGE_DTO_NEW_PASSWORD_WRAPPER_NULL = PasswordChangeDTO.builder()
        .currentPassword(VALID_PASSWORD_DTO)
        .newPasswordDetails(null)
        .build();

    private static final PasswordChangeDTO ERR_PASSWORD_CHANGE_DTO_NEW_PASSWORD_MATCHES_CURRENT = PasswordChangeDTO.builder()
        .currentPassword(VALID_PASSWORD_DTO)
        .newPasswordDetails(NewPasswordDetailsDTO.builder()
            .newPassword(VALID_PASSWORD_DTO)
            .confirmNewPassword(VALID_PASSWORD_DTO)
            .build())
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
        void testPasswordChangeDtoValid() {
            Set<ConstraintViolation<PasswordChangeDTO>> violations = validator.validate(VALID_PASSWORD_CHANGE_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPasswordChangeDtoInvalidCurrentPasswordNull() {
            Set<ConstraintViolation<PasswordChangeDTO>> violations = validator.validate(ERR_PASSWORD_CHANGE_DTO_CURRENT_PASSWORD_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CURRENT_PASSWORD_REQUIRED)));
        }

        @Test
        void testPasswordChangeDtoInvalidNewPasswordWrapperNull() {
            Set<ConstraintViolation<PasswordChangeDTO>> violations = validator.validate(ERR_PASSWORD_CHANGE_DTO_NEW_PASSWORD_WRAPPER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED)));
        }

        @Test
        void testPasswordChangeDtoInvalidNewPasswordMatchesCurrentPassword() {
            Set<ConstraintViolation<PasswordChangeDTO>> violations = validator.validate(ERR_PASSWORD_CHANGE_DTO_NEW_PASSWORD_MATCHES_CURRENT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_NEW_PASSWORD_MATCHES_CURRENT_PASSWORD)));
        }
    }
}
