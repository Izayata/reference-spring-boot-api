package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.DescriptionUtils.*;
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

public class DescriptionDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_EMPTY = new DescriptionDTO("");
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_NULL = new DescriptionDTO(null);
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_SPACE_ONLY = new DescriptionDTO("   ");
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_TOO_LONG = new DescriptionDTO("A".repeat(
        DESCRIPTION_VALUE_MAX_LENGTH + 1));
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_TOO_SHORT = new DescriptionDTO("A");
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_INVALID_LEADING_SPACE = new DescriptionDTO(" Invalid");
    private static final DescriptionDTO ERR_DESCRIPTION_DTO_INVALID_TRAILING_SPACE = new DescriptionDTO("Invalid ");
    private static final DescriptionDTO VALID_DESCRIPTION_DTO_INVALID_LEADING_SPECIAL_CHARACTER = new DescriptionDTO("@Invalid");
    private static final DescriptionDTO VALID_DESCRIPTION_DTO_INVALID_TRAILING_SPECIAL_CHARACTER = new DescriptionDTO("Invalid@");
    private static final DescriptionDTO VALID_DESCRIPTION_DTO_INVALID_CONTAINS_SPECIAL_CHARACTER = new DescriptionDTO("Inv@lid");
    public static final DescriptionDTO VALID_DESCRIPTION_DTO = new DescriptionDTO("Valid description");
    private static final DescriptionDTO VALID_DESCRIPTION_DTO_MIN_LENGTH = new DescriptionDTO("Val");
    private static final DescriptionDTO VALID_DESCRIPTION_DTO_MAX_LENGTH = new DescriptionDTO("A".repeat(
        DESCRIPTION_VALUE_MAX_LENGTH));
    private static final DescriptionDTO VALID_DESCRIPTION_DTO_HUN_LETTERS = new DescriptionDTO("Árvíztűrő tükörfúrógép");

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
        void testDescriptionDtoValidSimple() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(VALID_DESCRIPTION_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testDescriptionDtoValidMinLength() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(VALID_DESCRIPTION_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testDescriptionDtoValidMaxLength() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(VALID_DESCRIPTION_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testDescriptionDtoValidWithHungarianLetters() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(VALID_DESCRIPTION_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testDescriptionDtoValidLeadingSpecialCharacter() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(
                VALID_DESCRIPTION_DTO_INVALID_LEADING_SPECIAL_CHARACTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testDescriptionDtoValidTrailingSpecialCharacter() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(
                VALID_DESCRIPTION_DTO_INVALID_TRAILING_SPECIAL_CHARACTER);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testDescriptionDtoValidContainsSpecialCharacter() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(
                VALID_DESCRIPTION_DTO_INVALID_CONTAINS_SPECIAL_CHARACTER);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testDescriptionDtoInvalidEmpty() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_LENGTH)));
        }

        @Test
        void testDescriptionDtoInvalidNull() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_REQUIRED)));
        }

        @Test
        void testDescriptionDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testDescriptionDtoInvalidTooLong() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_LENGTH)));
        }

        @Test
        void testDescriptionDtoInvalidTooShort() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_LENGTH)));
        }

        @Test
        void testDescriptionDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_INVALID_LEADING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testDescriptionDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<DescriptionDTO>> violations = validator.validate(ERR_DESCRIPTION_DTO_INVALID_TRAILING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS)));
        }
    }
}
