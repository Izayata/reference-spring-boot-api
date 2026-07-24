package bar.imagine.demo.dto.customer;

import static bar.imagine.demo.dto.customer.personalDetails.FirstnameDTOTest.VALID_FIRSTNAME_DTO;
import static bar.imagine.demo.dto.customer.personalDetails.LastnameDTOTest.VALID_LASTNAME_DTO;
import static bar.imagine.demo.dto.customer.personalDetails.PhoneNumberDTOTest.VALID_PHONE_NUMBER_DTO_INTERNATIONAL_FORMAT;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_REQUIRED;
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

public class PersonalDetailsDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    public static final PersonalDetailsDTO VALID_PERSONAL_DETAILS_DTO = PersonalDetailsDTO.builder()
        .firstname(VALID_FIRSTNAME_DTO)
        .lastname(VALID_LASTNAME_DTO)
        .phoneNumber(VALID_PHONE_NUMBER_DTO_INTERNATIONAL_FORMAT)
        .build();

    private static final PersonalDetailsDTO ERR_PERSONAL_DETAILS_DTO_FIRSTNAME_NULL = PersonalDetailsDTO.builder()
        .firstname(null)
        .lastname(VALID_LASTNAME_DTO)
        .phoneNumber(VALID_PHONE_NUMBER_DTO_INTERNATIONAL_FORMAT)
        .build();

    private static final PersonalDetailsDTO ERR_PERSONAL_DETAILS_DTO_LASTNAME_NULL = PersonalDetailsDTO.builder()
        .firstname(VALID_FIRSTNAME_DTO)
        .lastname(null)
        .phoneNumber(VALID_PHONE_NUMBER_DTO_INTERNATIONAL_FORMAT)
        .build();

    private static final PersonalDetailsDTO ERR_PERSONAL_DETAILS_DTO_PHONE_NUMBER_NULL = PersonalDetailsDTO.builder()
        .firstname(VALID_FIRSTNAME_DTO)
        .lastname(VALID_LASTNAME_DTO)
        .phoneNumber(null)
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
        void testPersonalDetailsDtoValid() {
            Set<ConstraintViolation<PersonalDetailsDTO>> violations = validator.validate(VALID_PERSONAL_DETAILS_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPersonalDetailsDtoInvalidFirstnameNull() {
            Set<ConstraintViolation<PersonalDetailsDTO>> violations = validator.validate(ERR_PERSONAL_DETAILS_DTO_FIRSTNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FIRSTNAME_REQUIRED)));
        }

        @Test
        void testPersonalDetailsDtoInvalidLastnameNull() {
            Set<ConstraintViolation<PersonalDetailsDTO>> violations = validator.validate(ERR_PERSONAL_DETAILS_DTO_LASTNAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_LASTNAME_REQUIRED)));
        }

        @Test
        void testPersonalDetailsDtoInvalidPhoneNumberNull() {
            Set<ConstraintViolation<PersonalDetailsDTO>> violations = validator.validate(ERR_PERSONAL_DETAILS_DTO_PHONE_NUMBER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_REQUIRED)));
        }
    }
}
