package bar.imagine.demo.dto.food.allergen;

import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.*;
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

public class AllergenNameDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_EMPTY = new AllergenNameDTO("");
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_NULL = new AllergenNameDTO(null);
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_SPACE_ONLY = new AllergenNameDTO("  ");
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_TOO_LONG = new AllergenNameDTO("A".repeat(
        ALLERGEN_NAME_VALUE_MAX_LENGTH + 1));
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_TOO_SHORT = new AllergenNameDTO("A");
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_INVALID_DIGIT = new AllergenNameDTO("Allergen1");
    private static final AllergenNameDTO ERR_ALLERGEN_NAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER = new AllergenNameDTO("Allergen!");
    public static final AllergenNameDTO VALID_ALLERGEN_NAME_DTO = new AllergenNameDTO("Allergen");
    private static final AllergenNameDTO VALID_ALLERGEN_NAME_DTO_MIN_LENGTH = new AllergenNameDTO("Al");
    private static final AllergenNameDTO VALID_ALLERGEN_NAME_DTO_MAX_LENGTH = new AllergenNameDTO("A".repeat(
        ALLERGEN_NAME_VALUE_MAX_LENGTH));
    private static final AllergenNameDTO VALID_ALLERGEN_NAME_DTO_HUN_LETTERS = new AllergenNameDTO("Banán héja");

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
        void testAllergenNameDtoValidSimple() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(VALID_ALLERGEN_NAME_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testAllergenNameDtoValidMinLength() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(VALID_ALLERGEN_NAME_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testAllergenNameDtoValidMaxLength() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(VALID_ALLERGEN_NAME_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testAllergenNameDtoValidWithHungarianLetters() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(VALID_ALLERGEN_NAME_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testAllergenNameDtoInvalidEmpty() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_LENGTH)));
        }

        @Test
        void testAllergenNameDtoInvalidNull() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_VALUE_REQUIRED)));
        }

        @Test
        void testAllergenNameDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS)));
        }

        @Test
        void testAllergenNameDtoInvalidTooLong() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_LENGTH)));
        }

        @Test
        void testAllergenNameDtoInvalidTooShort() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_LENGTH)));
        }

        @Test
        void testAllergenNameDtoInvalidCharacterDigit() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_INVALID_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS)));
        }

        @Test
        void testAllergenNameDtoInvalidCharacterBannedSpecialCharacter() {
            Set<ConstraintViolation<AllergenNameDTO>> violations = validator.validate(ERR_ALLERGEN_NAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS)));
        }
    }
}
