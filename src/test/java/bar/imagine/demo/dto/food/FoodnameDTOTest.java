package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.FoodNameUtils.*;
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

public class FoodnameDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_EMPTY = new FoodNameDTO("");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_NULL = new FoodNameDTO(null);
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_SPACE_ONLY = new FoodNameDTO("   ");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_TOO_LONG = new FoodNameDTO("A".repeat(
        FOOD_NAME_VALUE_MAX_LENGTH + 1));
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_TOO_SHORT = new FoodNameDTO("A");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_INVALID_LEADING_SPACE = new FoodNameDTO(" Invalid");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_INVALID_TRAILING_SPACE = new FoodNameDTO("Invalid ");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_INVALID_LEADING_SPECIAL_CHARACTER = new FoodNameDTO("@Invalid");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_INVALID_TRAILING_SPECIAL_CHARACTER = new FoodNameDTO("Invalid@");
    private static final FoodNameDTO ERR_FOOD_NAME_DTO_INVALID_CONTAINS_SPECIAL_CHARACTER = new FoodNameDTO("Inv@lid");
    public static final FoodNameDTO VALID_FOOD_NAME_DTO = new FoodNameDTO("Valid Food Name");
    private static final FoodNameDTO VALID_FOOD_NAME_DTO_MIN_LENGTH = new FoodNameDTO("Foo");
    private static final FoodNameDTO VALID_FOOD_NAME_DTO_MAX_LENGTH = new FoodNameDTO("A".repeat(
        FOOD_NAME_VALUE_MAX_LENGTH));
    private static final FoodNameDTO VALID_FOOD_NAME_DTO_HUN_LETTERS = new FoodNameDTO("Árvíztűrő tükörfúrógép");

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
        void testFoodNameDtoValidSimple() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(VALID_FOOD_NAME_DTO);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFoodNameDtoValidMinLength() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(VALID_FOOD_NAME_DTO_MIN_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFoodNameDtoValidMaxLength() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(VALID_FOOD_NAME_DTO_MAX_LENGTH);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testFoodNameDtoValidWithHungarianLetters() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(VALID_FOOD_NAME_DTO_HUN_LETTERS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testFoodNameDtoInvalidEmpty() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_EMPTY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_LENGTH)));
        }

        @Test
        void testFoodNameDtoInvalidNull() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_REQUIRED)));
        }

        @Test
        void testFoodNameDtoInvalidSpaceOnly() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_SPACE_ONLY);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testFoodNameDtoInvalidTooLong() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_TOO_LONG);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_LENGTH)));
        }

        @Test
        void testFoodNameDtoInvalidTooShort() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_TOO_SHORT);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_LENGTH)));
        }

        @Test
        void testFoodNameDtoInvalidLeadingSpace() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_INVALID_LEADING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testFoodNameDtoInvalidTrailingSpace() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_INVALID_TRAILING_SPACE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testFoodNameDtoInvalidLeadingSpecialCharacter() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_INVALID_LEADING_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testFoodNameDtoInvalidTrailingSpecialCharacter() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_INVALID_TRAILING_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
        }

        @Test
        void testFoodNameDtoInvalidSpecialCharacter() {
            Set<ConstraintViolation<FoodNameDTO>> violations = validator.validate(ERR_FOOD_NAME_DTO_INVALID_CONTAINS_SPECIAL_CHARACTER);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS)));
        }
    }
}
