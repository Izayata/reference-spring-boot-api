package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_VALUE_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import bar.imagine.demo.dto.customer.address.ZipCodeDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class ZipCodeDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final ZipCodeDTO ERR_ZIP_DTO_EMPTY = new ZipCodeDTO("");
    private static final ZipCodeDTO ERR_ZIP_DTO_NULL = new ZipCodeDTO(null);
    private static final ZipCodeDTO ERR_ZIP_DTO_SPACE_ONLY = new ZipCodeDTO("    ");
    private static final ZipCodeDTO ERR_ZIP_DTO_TOO_LONG = new ZipCodeDTO("40322");
    private static final ZipCodeDTO ERR_ZIP_DTO_TOO_SHORT = new ZipCodeDTO("403");
    private static final ZipCodeDTO ERR_ZIP_DTO_LEADING_SPACES = new ZipCodeDTO(" 4032");
    private static final ZipCodeDTO ERR_ZIP_DTO_TRAILING_SPACES = new ZipCodeDTO("4032 ");
    private static final ZipCodeDTO ERR_ZIP_DTO_LEADING_TRAILING_SPACES = new ZipCodeDTO(" 4032 ");
    private static final ZipCodeDTO ERR_ZIP_DTO_CONTAINS_SPACES = new ZipCodeDTO(" 4032 ");
    private static final ZipCodeDTO ERR_ZIP_DTO_INVALID_SPECIAL_CHARACTER = new ZipCodeDTO("403!");
    private static final ZipCodeDTO ERR_ZIP_DTO_INVALID_LETTER = new ZipCodeDTO("A403");
    public static final ZipCodeDTO VALID_ZIP_DTO = new ZipCodeDTO("4032");

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
        void testValidZipCode() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(VALID_ZIP_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testZipCodeDtoInvalidEmpty() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidNull() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_VALUE_REQUIRED)));
        }

        @Test
        void testZipCodeDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidTooLong() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidTooShort() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidContainsSpace() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidContainsSpecialCharacter() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_INVALID_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeDtoInvalidContainsLetter() {
            Set<ConstraintViolation<ZipCodeDTO>> violations = validator.validate(ERR_ZIP_DTO_INVALID_LETTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }
    }
}
