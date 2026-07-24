package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.FLOOR_DOOR_MAX_LENGTH;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import bar.imagine.demo.dto.customer.address.FloorDoorDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class FloorDoorDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_EMPTY = new FloorDoorDTO("");
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_NULL = new FloorDoorDTO(null);
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_SPACE_ONLY = new FloorDoorDTO(" ");
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_TOO_LONG = new FloorDoorDTO("A".repeat(FLOOR_DOOR_MAX_LENGTH + 1));
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_LEADING_SPACES = new FloorDoorDTO(" 21/A");
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_TRAILING_SPACES = new FloorDoorDTO("21/A ");
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_LEADING_TRAILING_SPACES = new FloorDoorDTO(" 21/A ");
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_CONTAINS_SPACES = new FloorDoorDTO("2 1/A");
    private static final FloorDoorDTO ERR_FLOOR_DTO_DOOR_INVALID_FORMAT = new FloorDoorDTO("2//B");
    public static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_NUMBER_SLASH_NUMBER = new FloorDoorDTO("2/8");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_LETTER_SLASH_LETTER = new FloorDoorDTO("A/B");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_LETTER_SLASH_NUMBER = new FloorDoorDTO("A/1");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_NUMBER_SLASH_LETTER = new FloorDoorDTO("1/B");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_NUMBER_NUMBER_HUN_LETTER_SLASH_HUN_LETTER = new FloorDoorDTO("21Á/ű");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_DASH_NUMBER_SLASH_LETTER = new FloorDoorDTO("-1/B");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_NUMBER_LETTER = new FloorDoorDTO("1B");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_DASH_NUMBER_LETTER = new FloorDoorDTO("-1a");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_NUMBER_FLOOR_ONLY = new FloorDoorDTO("11");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_MIN_LENGTH_NUMBER_ONLY = new FloorDoorDTO("2");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_MIN_LENGTH_LETTER_ONLY = new FloorDoorDTO("A");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_MAX_LENGTH = new FloorDoorDTO("A".repeat(FLOOR_DOOR_MAX_LENGTH));
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_MIN_LENGTH_HUN_LETTER_ONLY = new FloorDoorDTO("á");
    private static final FloorDoorDTO VALID_FLOOR_DOOR_DTO_HUN_LETTER_SLASH_HUN_LETTER = new FloorDoorDTO("áÉű/Ó");

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
        void testFloorDoorDtoValidNumberSlashNumber() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_NUMBER_SLASH_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidDashNumberSlashNumber() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_DASH_NUMBER_SLASH_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidLetterSlashLetter() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_LETTER_SLASH_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidNumberLetter() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_NUMBER_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidDashNumberLetter() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_DASH_NUMBER_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidHungarianLetters() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_HUN_LETTER_SLASH_HUN_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidLetterSlashNumber() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_LETTER_SLASH_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidNumberSlashLetter() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_NUMBER_SLASH_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidMinLengthLetter() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_MIN_LENGTH_LETTER_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidMinLengthNumber() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_MIN_LENGTH_NUMBER_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidMinLengthHunLetter() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_MIN_LENGTH_HUN_LETTER_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidNumberOnly() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_NUMBER_FLOOR_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorDtoValidMaxLength() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testValidFloorDoorDtoWithHungarianAndDigits() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(VALID_FLOOR_DOOR_DTO_NUMBER_NUMBER_HUN_LETTER_SLASH_HUN_LETTER);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testFloorDoorDtoInvalidEmpty() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_LENGTH)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorDtoInvalidNull() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)));
        }

        @Test
        void testFloorDoorDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorDtoInvalidTooLong() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_LENGTH)));
        }

        @Test
        void testFloorDoorDtoInvalidLeadingSpaces() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorDtoInvalidTrailingSpaces() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorDtoInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorDtoInvalidContainsSpaces() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorDtoInvalidFormat() {
            Set<ConstraintViolation<FloorDoorDTO>> violations = validator.validate(ERR_FLOOR_DTO_DOOR_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }
    }
}
