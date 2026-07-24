package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_VALUE_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import bar.imagine.demo.data.customer.address.ZipCode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class ZipCodeTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final ZipCode ERR_ZIP_EMPTY = new ZipCode("");
    private static final ZipCode ERR_ZIP_NULL = new ZipCode(null);
    private static final ZipCode ERR_ZIP_SPACE_ONLY = new ZipCode("    ");
    private static final ZipCode ERR_ZIP_TOO_LONG = new ZipCode("40288");
    private static final ZipCode ERR_ZIP_TOO_SHORT = new ZipCode("402");
    private static final ZipCode ERR_ZIP_LEADING_SPACES = new ZipCode(" 4028");
    private static final ZipCode ERR_ZIP_TRAILING_SPACES = new ZipCode("4028 ");
    private static final ZipCode ERR_ZIP_LEADING_TRAILING_SPACES = new ZipCode(" 4028 ");
    private static final ZipCode ERR_ZIP_CONTAINS_SPACES = new ZipCode(" 4028 ");
    private static final ZipCode ERR_ZIP_INVALID_SPECIAL_CHARACTER = new ZipCode("402!");
    private static final ZipCode ERR_ZIP_INVALID_LETTER = new ZipCode("A402");
    public static final ZipCode VALID_ZIP = new ZipCode("4028");

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
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(VALID_ZIP);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testZipCodeInvalidEmpty() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidNull() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_VALUE_REQUIRED)));
        }

        @Test
        void testZipCodeInvalidSpaceOnly() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidTooLong() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidTooShort() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidLeadingSpace() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidTrailingSpace() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidLeadingTrailingSpace() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidContainsSpace() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidContainsSpecialCharacter() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_INVALID_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }

        @Test
        void testZipCodeInvalidContainsLetter() {
            Set<ConstraintViolation<ZipCode>> violations = validator.validate(ERR_ZIP_INVALID_LETTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_INVALID_FORMAT)));
        }
    }
}
