package bar.imagine.demo.dto.food.ingredient;

import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.*;
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

public class IngredientNameDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_EMPTY = new IngredientNameDTO("");
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_NULL = new IngredientNameDTO(null);
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_SPACE_ONLY = new IngredientNameDTO("  ");
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_TOO_LONG = new IngredientNameDTO("A".repeat(
        INGREDIENT_VALUE_MAX_LENGTH + 1));
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_TOO_SHORT = new IngredientNameDTO("A");
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_INVALID_DIGIT = new IngredientNameDTO("Ingredient1");
    private static final IngredientNameDTO ERR_INGREDIENT_NAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER = new IngredientNameDTO("Ingredient!");
    public static final IngredientNameDTO VALID_INGREDIENT_NAME_DTO = new IngredientNameDTO("Ingredient");
    private static final IngredientNameDTO VALID_INGREDIENT_NAME_DTO_MIN_LENGTH = new IngredientNameDTO("In");
    private static final IngredientNameDTO VALID_INGREDIENT_NAME_DTO_MAX_LENGTH = new IngredientNameDTO("A".repeat(
        INGREDIENT_VALUE_MAX_LENGTH));
    private static final IngredientNameDTO VALID_INGREDIENT_NAME_DTO_HUN_LETTERS = new IngredientNameDTO("Banán héja");

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
        void testIngredientNameDtoValidSimple() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(VALID_INGREDIENT_NAME_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testIngredientNameDtoValidMinLength() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(VALID_INGREDIENT_NAME_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testIngredientNameDtoValidMaxLength() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(VALID_INGREDIENT_NAME_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testIngredientNameDtoValidWithHungarianLetters() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(VALID_INGREDIENT_NAME_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testIngredientNameDtoInvalidEmpty() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_LENGTH)));
        }

        @Test
        void testIngredientNameDtoInvalidNull() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_REQUIRED)));
        }

        @Test
        void testIngredientNameDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testIngredientNameDtoInvalidTooLong() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_LENGTH)));
        }

        @Test
        void testIngredientNameDtoInvalidTooShort() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_LENGTH)));
        }

        @Test
        void testIngredientNameDtoInvalidCharacterDigit() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_INVALID_DIGIT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testIngredientNameDtoInvalidCharacterBannedSpecialCharacter() {
            Set<ConstraintViolation<IngredientNameDTO>> violations = validator.validate(ERR_INGREDIENT_NAME_DTO_INVALID_BANNED_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS)));
        }
    }
}
