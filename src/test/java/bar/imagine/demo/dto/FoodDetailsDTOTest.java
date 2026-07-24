package bar.imagine.demo.dto;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_NAME_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_DESCRIPTION_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_INGREDIENT_NAMES_LIST_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_ALLERGEN_LIST_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_IMAGE_URL_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.dto.food.*;
import bar.imagine.demo.dto.food.allergen.AllergenNameDTO;
import bar.imagine.demo.dto.food.ingredient.IngredientNameDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class FoodDetailsDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final AllergenDTO ALLERGEN_DTO = AllergenDTO.builder()
        .id(1L)
        .name(new AllergenNameDTO("Gluten"))
        .build();

    private static final FoodDetailsDTO VALID_FOOD_DETAILS_DTO = FoodDetailsDTO.builder()
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("10.50"), CurrencyEnum.USD))
        .description(new DescriptionDTO("Delicious pizza"))
        .ingredientNames(List.of(new IngredientNameDTO("Cheese"), new IngredientNameDTO("Tomato")))
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final FoodDetailsDTO ERR_FOOD_DETAILS_DTO_NAME_NULL = FoodDetailsDTO.builder()
        .foodName(null)
        .price(new PriceDTO(new BigDecimal("10.50"), CurrencyEnum.USD))
        .description(new DescriptionDTO("Delicious pizza"))
        .ingredientNames(List.of(new IngredientNameDTO("Cheese"), new IngredientNameDTO("Tomato")))
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final FoodDetailsDTO ERR_FOOD_DETAILS_DTO_PRICE_NULL = FoodDetailsDTO.builder()
        .foodName(new FoodNameDTO("Pizza"))
        .price(null)
        .description(new DescriptionDTO("Delicious pizza"))
        .ingredientNames(List.of(new IngredientNameDTO("Cheese"), new IngredientNameDTO("Tomato")))
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final FoodDetailsDTO ERR_FOOD_DETAILS_DTO_DESCRIPTION_NULL = FoodDetailsDTO.builder()
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("10.50"), CurrencyEnum.USD))
        .description(null)
        .ingredientNames(List.of(new IngredientNameDTO("Cheese"), new IngredientNameDTO("Tomato")))
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final FoodDetailsDTO ERR_FOOD_DETAILS_DTO_INGREDIENTS_NULL = FoodDetailsDTO.builder()
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("10.50"), CurrencyEnum.USD))
        .description(new DescriptionDTO("Delicious pizza"))
        .ingredientNames(null)
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final FoodDetailsDTO ERR_FOOD_DETAILS_DTO_ALLERGENS_NULL = FoodDetailsDTO.builder()
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("10.50"), CurrencyEnum.USD))
        .description(new DescriptionDTO("Delicious pizza"))
        .ingredientNames(List.of(new IngredientNameDTO("Cheese"), new IngredientNameDTO("Tomato")))
        .allergens(null)
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final FoodDetailsDTO ERR_FOOD_DETAILS_DTO_IMAGE_URL_NULL = FoodDetailsDTO.builder()
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("10.50"), CurrencyEnum.USD))
        .description(new DescriptionDTO("Delicious pizza"))
        .ingredientNames(List.of(new IngredientNameDTO("Cheese"), new IngredientNameDTO("Tomato")))
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(null)
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
        void testFoodDetailsDtoValid() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(VALID_FOOD_DETAILS_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testFoodDetailsDtoInvalidNameNull() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(ERR_FOOD_DETAILS_DTO_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_REQUIRED)));
        }

        @Test
        void testFoodDetailsDtoInvalidPriceNull() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(ERR_FOOD_DETAILS_DTO_PRICE_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_REQUIRED)));
        }

        @Test
        void testFoodDetailsDtoInvalidDescriptionNull() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(ERR_FOOD_DETAILS_DTO_DESCRIPTION_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_DESCRIPTION_REQUIRED)));
        }

        @Test
        void testFoodDetailsDtoInvalidIngredientsNull() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(ERR_FOOD_DETAILS_DTO_INGREDIENTS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_INGREDIENT_NAMES_LIST_REQUIRED)));
        }

        @Test
        void testFoodDetailsDtoInvalidAllergensNull() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(ERR_FOOD_DETAILS_DTO_ALLERGENS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_LIST_REQUIRED)));
        }

        @Test
        void testFoodDetailsDtoInvalidImageUrlNull() {
            Set<ConstraintViolation<FoodDetailsDTO>> violations = validator.validate(ERR_FOOD_DETAILS_DTO_IMAGE_URL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_REQUIRED)));
        }
    }
}
