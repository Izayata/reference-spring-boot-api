package bar.imagine.demo.dto.food;

import static bar.imagine.demo.dto.food.ingredient.IngredientNameDTOTest.VALID_INGREDIENT_NAME_DTO;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_ID_REQUIRED;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_NAME_REQUIRED;
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

class IngredientDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final IngredientDTO ERR_INGREDIENT_DTO_ID_NULL = IngredientDTO.builder()
        .id(null)
        .name(VALID_INGREDIENT_NAME_DTO)
        .build();
    private static final IngredientDTO ERR_INGREDIENT_DTO_NAME_NULL = IngredientDTO.builder()
        .id(1L)
        .name(null)
        .build();
    private static final IngredientDTO ERR_INGREDIENT_DTO_ID_AND_NAME_NULL = IngredientDTO.builder()
        .id(null)
        .name(null)
        .build();
    private static final IngredientDTO VALID_INGREDIENT_DTO = IngredientDTO.builder()
        .id(1L)
        .name(VALID_INGREDIENT_NAME_DTO)
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
        void testIngredientDtoValid() {
            Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(VALID_INGREDIENT_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testIngredientDtoInvalidIdNull() {
            Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(ERR_INGREDIENT_DTO_ID_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_ID_REQUIRED)));
        }

        @Test
        void testIngredientDtoInvalidNameNull() {
            Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(ERR_INGREDIENT_DTO_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_NAME_REQUIRED)));
        }

        @Test
        void testIngredientDtoInvalidIdAndNameNull() {
            Set<ConstraintViolation<IngredientDTO>> violations = validator.validate(ERR_INGREDIENT_DTO_ID_AND_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_ID_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_NAME_REQUIRED)));
        }
    }
}
