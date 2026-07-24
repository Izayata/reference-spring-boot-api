package bar.imagine.demo.dto;

import static bar.imagine.demo.dto.EmailDTOTest.VALID_EMAIL_DTO;
import static bar.imagine.demo.dto.myUser.MyUsernameDTOTest.VALID_MY_USERNAME_DTO;
import static bar.imagine.demo.dto.myUser.PasswordDTOTest.VALID_PASSWORD_DTO;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.NewPasswordDetailsUtils.ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED;
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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class MyUserRegistrationDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final NewPasswordDetailsDTO VALID_NEW_PASSWORD_DETAILS_DTO = NewPasswordDetailsDTO.builder()
        .newPassword(VALID_PASSWORD_DTO)
        .confirmNewPassword(VALID_PASSWORD_DTO)
        .build();

    public static final MyUserRegistrationDTO VALID_MY_USER_REGISTRATION_DTO = MyUserRegistrationDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(VALID_MY_USERNAME_DTO)
        .newPasswordDetails(VALID_NEW_PASSWORD_DETAILS_DTO)
        .build();

    private static final MyUserRegistrationDTO ERR_MY_USER_REGISTRATION_DTO_EMAIL_NULL = MyUserRegistrationDTO.builder()
        .email(null)
        .myUsername(VALID_MY_USERNAME_DTO)
        .newPasswordDetails(VALID_NEW_PASSWORD_DETAILS_DTO)
        .build();

    private static final MyUserRegistrationDTO ERR_MY_USER_REGISTRATION_DTO_MY_USERNAME_NULL = MyUserRegistrationDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(null)
        .newPasswordDetails(VALID_NEW_PASSWORD_DETAILS_DTO)
        .build();

    private static final MyUserRegistrationDTO ERR_MY_USER_REGISTRATION_DTO_PASSWORD_NULL = MyUserRegistrationDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(VALID_MY_USERNAME_DTO)
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


    @Nested
    @DisplayName("Valid")
    class Valid {

        @Test
        void testValidMyUserRegistrationDto() {
            Set<ConstraintViolation<MyUserRegistrationDTO>> violations = validator.validate(VALID_MY_USER_REGISTRATION_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testInvalidEmailNull() {
            Set<ConstraintViolation<MyUserRegistrationDTO>> violations = validator.validate(ERR_MY_USER_REGISTRATION_DTO_EMAIL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testInvalidMyUsernameNull() {
            Set<ConstraintViolation<MyUserRegistrationDTO>> violations = validator.validate(ERR_MY_USER_REGISTRATION_DTO_MY_USERNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_REQUIRED)));
        }

        @Test
        void testInvalidPasswordNull() {
            Set<ConstraintViolation<MyUserRegistrationDTO>> violations = validator.validate(ERR_MY_USER_REGISTRATION_DTO_PASSWORD_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED)));
        }
    }
}
