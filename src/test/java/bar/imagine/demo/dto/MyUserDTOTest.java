package bar.imagine.demo.dto;

import static bar.imagine.demo.dto.EmailDTOTest.VALID_EMAIL_DTO;
import static bar.imagine.demo.dto.myUser.MyUsernameDTOTest.VALID_MY_USERNAME_DTO;
import static bar.imagine.demo.dto.CustomerDTOTest.VALID_CUSTOMER_DTO;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.MyUserUtils.ERR_MSG_CUSTOMER_REQUIRED;
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

class MyUserDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    public static final MyUserDTO VALID_MY_USER_DTO = MyUserDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(VALID_MY_USERNAME_DTO)
        .customer(VALID_CUSTOMER_DTO)
        .build();

    private static final MyUserDTO ERR_MY_USER_DTO_EMAIL_NULL = MyUserDTO.builder()
        .email(null)
        .myUsername(VALID_MY_USERNAME_DTO)
        .customer(VALID_CUSTOMER_DTO)
        .build();

    private static final MyUserDTO ERR_MY_USER_DTO_MY_USERNAME_NULL = MyUserDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(null)
        .customer(VALID_CUSTOMER_DTO)
        .build();

    private static final MyUserDTO ERR_MY_USER_DTO_PASSWORD_NULL = MyUserDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(VALID_MY_USERNAME_DTO)
        .customer(VALID_CUSTOMER_DTO)
        .build();

    private static final MyUserDTO ERR_MY_USER_DTO_CUSTOMER_NULL = MyUserDTO.builder()
        .email(VALID_EMAIL_DTO)
        .myUsername(VALID_MY_USERNAME_DTO)
        .customer(null)
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
        void testValidMyUserDto() {
            Set<ConstraintViolation<MyUserDTO>> violations = validator.validate(VALID_MY_USER_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testInvalidEmailNull() {
            Set<ConstraintViolation<MyUserDTO>> violations = validator.validate(ERR_MY_USER_DTO_EMAIL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_EMAIL_REQUIRED)));
        }

        @Test
        void testInvalidMyUsernameNull() {
            Set<ConstraintViolation<MyUserDTO>> violations = validator.validate(ERR_MY_USER_DTO_MY_USERNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_USERNAME_REQUIRED)));
        }

        @Test
        void testInvalidCustomerNull() {
            Set<ConstraintViolation<MyUserDTO>> violations = validator.validate(ERR_MY_USER_DTO_CUSTOMER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CUSTOMER_REQUIRED)));
        }
    }
}
