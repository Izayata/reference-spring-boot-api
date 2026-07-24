package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.FloorDoorUtils.FLOOR_DOOR_MAX_LENGTH;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import bar.imagine.demo.data.customer.address.FloorDoor;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

public class FloorDoorTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final FloorDoor ERR_FLOOR_DOOR_EMPTY = new FloorDoor("");
    private static final FloorDoor ERR_FLOOR_DOOR_NULL = new FloorDoor(null);
    private static final FloorDoor ERR_FLOOR_DOOR_SPACE_ONLY = new FloorDoor(" ");
    private static final FloorDoor ERR_FLOOR_DOOR_TOO_LONG = new FloorDoor("A".repeat(FLOOR_DOOR_MAX_LENGTH + 1));
    private static final FloorDoor ERR_FLOOR_DOOR_LEADING_SPACES = new FloorDoor(" 12/A");
    private static final FloorDoor ERR_FLOOR_DOOR_TRAILING_SPACES = new FloorDoor("12/A ");
    private static final FloorDoor ERR_FLOOR_DOOR_LEADING_TRAILING_SPACES = new FloorDoor(" 12/A ");
    private static final FloorDoor ERR_FLOOR_DOOR_CONTAINS_SPACES = new FloorDoor("1 2/A");
    private static final FloorDoor ERR_FLOOR_DOOR_INVALID_FORMAT = new FloorDoor("2//B");
    public static final FloorDoor VALID_FLOOR_DOOR_NUMBER_SLASH_NUMBER = new FloorDoor("2/8");
    private static final FloorDoor VALID_FLOOR_DOOR_LETTER_SLASH_LETTER = new FloorDoor("A/B");
    private static final FloorDoor VALID_FLOOR_DOOR_LETTER_SLASH_NUMBER = new FloorDoor("A/1");
    private static final FloorDoor VALID_FLOOR_DOOR_NUMBER_SLASH_LETTER = new FloorDoor("1/B");
    private static final FloorDoor VALID_FLOOR_DOOR_NUMBER__NUMBER_HUN_LETTER_SLASH_HUN_LETTER = new FloorDoor("12Á/ű");
    private static final FloorDoor VALID_FLOOR_DOOR_DASH_NUMBER_SLASH_LETTER = new FloorDoor("-1/B");
    private static final FloorDoor VALID_FLOOR_DOOR_NUMBER_LETTER = new FloorDoor("1B");
    private static final FloorDoor VALID_FLOOR_DOOR_DASH_NUMBER_LETTER = new FloorDoor("-1a");
    private static final FloorDoor VALID_FLOOR_DOOR_NUMBER_FLOOR_ONLY = new FloorDoor("11");
    private static final FloorDoor VALID_FLOOR_DOOR_MIN_LENGTH_NUMBER_ONLY = new FloorDoor("2");
    private static final FloorDoor VALID_FLOOR_DOOR_MIN_LENGTH_LETTER_ONLY = new FloorDoor("A");
    private static final FloorDoor VALID_FLOOR_DOOR_MAX_LENGTH = new FloorDoor("A".repeat(FLOOR_DOOR_MAX_LENGTH));
    private static final FloorDoor VALID_FLOOR_DOOR_MIN_LENGTH_HUN_LETTER_ONLY = new FloorDoor("á");
    private static final FloorDoor VALID_FLOOR_DOOR_HUN_LETTER_SLASH_HUN_LETTER = new FloorDoor("áÉű/Ó");

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
        void testFloorDoorValidNumberSlashNumber() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_NUMBER_SLASH_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidDashNumberSlashNumber() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_DASH_NUMBER_SLASH_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidLetterSlashLetter() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_LETTER_SLASH_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidNumberLetter() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_NUMBER_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidDashNumberLetter() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_DASH_NUMBER_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidHungarianLetters() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_HUN_LETTER_SLASH_HUN_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidLetterSlashNumber() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_LETTER_SLASH_NUMBER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidNumberSlashLetter() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_NUMBER_SLASH_LETTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidMinLengthLetter() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_MIN_LENGTH_LETTER_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidMinLengthNumber() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_MIN_LENGTH_NUMBER_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidMinLengthHunLetter() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_MIN_LENGTH_HUN_LETTER_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidNumberOnly() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_NUMBER_FLOOR_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFloorDoorValidMaxLength() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testValidFloorDoorWithHungarianAndDigits() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(VALID_FLOOR_DOOR_NUMBER__NUMBER_HUN_LETTER_SLASH_HUN_LETTER);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testFloorDoorInvalidEmpty() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_LENGTH)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorInvalidNull() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)));
        }

        @Test
        void testFloorDoorInvalidSpaceOnly() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorInvalidTooLong() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_LENGTH)));
        }

        @Test
        void testFloorDoorInvalidLeadingSpaces() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_LEADING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorInvalidTrailingSpaces() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorInvalidLeadingTrailingSpaces() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_LEADING_TRAILING_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorInvalidContainsSpaces() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_CONTAINS_SPACES);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }

        @Test
        void testFloorDoorInvalidFormat() {
            Set<ConstraintViolation<FloorDoor>> violations = validator.validate(ERR_FLOOR_DOOR_INVALID_FORMAT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FLOOR_DOOR_INVALID_FORMAT)));
        }
    }
}
