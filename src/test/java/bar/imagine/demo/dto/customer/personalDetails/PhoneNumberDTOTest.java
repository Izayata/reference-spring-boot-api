package bar.imagine.demo.dto.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_VALUE_INVALID;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED;
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

public class PhoneNumberDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_EMPTY = new PhoneNumberDTO("");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_NULL = new PhoneNumberDTO(null);
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_SPACE_ONLY = new PhoneNumberDTO(" ");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_LEADING_SPACES = new PhoneNumberDTO(" +36707316483");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_TRAILING_SPACES = new PhoneNumberDTO("+36707316483 ");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_LEADING_TRAILING_SPACES = new PhoneNumberDTO(" +36707316483 ");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_CONTAINS_SPACES = new PhoneNumberDTO("+3670 7316483");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_NO_PLUS_SIGN = new PhoneNumberDTO("36707316483");
    private static final PhoneNumberDTO ERR_PHONE_NUMBER_DTO_INVALID_FORMAT = new PhoneNumberDTO("+123-abc-7890");
    public static final PhoneNumberDTO VALID_PHONE_NUMBER_DTO_INTERNATIONAL_FORMAT = new PhoneNumberDTO("+36707316483");
    public static final PhoneNumberDTO VALID_PHONE_NUMBER_DTO_INTRA_FORMAT_HU = new PhoneNumberDTO("06707316483");

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
        void testPhoneNumberDtoValidInternationalFormat() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(VALID_PHONE_NUMBER_DTO_INTERNATIONAL_FORMAT);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testPhoneNumberDtoValidIntraFormatHU() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(VALID_PHONE_NUMBER_DTO_INTRA_FORMAT_HU);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testPhoneNumberDtoValidNoPlusSign() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_NO_PLUS_SIGN);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPhoneNumberDtoInvalidEmpty() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_INVALID)));
        }

        @Test
        void testPhoneNumberDtoInvalidNull() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)));
            // @ValidPhoneNumber returns true for null (null-safe by design), so VALUE_INVALID is not produced
        }

        @Test
        void testPhoneNumberDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_INVALID)));
        }

        @Test
        void testPhoneNumberDtoInvalidLeadingSpaces() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberDtoInvalidTrailingSpaces() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberDtoInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberDtoInvalidContainsSpaces() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }

        @Test
        void testPhoneNumberDtoInvalidFormat() {
            Set<ConstraintViolation<PhoneNumberDTO>> violations = validator.validate(ERR_PHONE_NUMBER_DTO_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_VALUE_INVALID)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)));
        }
    }
}
