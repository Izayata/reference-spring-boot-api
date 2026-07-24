package bar.imagine.demo.dto;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_ID_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_NAME_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_CATEGORY_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_ALLERGEN_LIST_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_IMAGE_URL_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.dto.food.FoodNameDTO;
import bar.imagine.demo.dto.food.ImageURLDTO;
import bar.imagine.demo.dto.food.PriceDTO;
import bar.imagine.demo.dto.food.allergen.AllergenNameDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class MenuItemDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final AllergenDTO ALLERGEN_DTO = AllergenDTO.builder()
        .id(1L)
        .name(new AllergenNameDTO("Gluten"))
        .build();

    private static final MenuItemDTO VALID_MENU_ITEM_DTO = MenuItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Burger"))
        .price(new PriceDTO(new BigDecimal("5.99"), CurrencyEnum.USD))
        .category(CategoryEnum.MAIN_DISHES)
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final MenuItemDTO ERR_MENU_ITEM_DTO_FOOD_ID_NULL = MenuItemDTO.builder()
        .foodId(null)
        .foodName(new FoodNameDTO("Burger"))
        .price(new PriceDTO(new BigDecimal("5.99"), CurrencyEnum.USD))
        .category(CategoryEnum.MAIN_DISHES)
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final MenuItemDTO ERR_MENU_ITEM_DTO_FOOD_NAME_NULL = MenuItemDTO.builder()
        .foodId(1L)
        .foodName(null)
        .price(new PriceDTO(new BigDecimal("5.99"), CurrencyEnum.USD))
        .category(CategoryEnum.MAIN_DISHES)
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final MenuItemDTO ERR_MENU_ITEM_DTO_PRICE_NULL = MenuItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Burger"))
        .price(null)
        .category(CategoryEnum.MAIN_DISHES)
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final MenuItemDTO ERR_MENU_ITEM_DTO_CATEGORY_NULL = MenuItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Burger"))
        .price(new PriceDTO(new BigDecimal("5.99"), CurrencyEnum.USD))
        .category(null)
        .allergens(List.of(ALLERGEN_DTO))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final MenuItemDTO ERR_MENU_ITEM_DTO_ALLERGEN_IDS_NULL = MenuItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Burger"))
        .price(new PriceDTO(new BigDecimal("5.99"), CurrencyEnum.USD))
        .category(CategoryEnum.MAIN_DISHES)
        .allergens(null)
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final MenuItemDTO ERR_MENU_ITEM_DTO_IMAGE_URL_NULL = MenuItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Burger"))
        .price(new PriceDTO(new BigDecimal("5.99"), CurrencyEnum.USD))
        .category(CategoryEnum.MAIN_DISHES)
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
        void testMenuItemDtoValid() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(VALID_MENU_ITEM_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testMenuItemDtoInvalidFoodIdNull() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(ERR_MENU_ITEM_DTO_FOOD_ID_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_ID_REQUIRED)));
        }

        @Test
        void testMenuItemDtoInvalidFoodNameNull() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(ERR_MENU_ITEM_DTO_FOOD_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_REQUIRED)));
        }

        @Test
        void testMenuItemDtoInvalidPriceNull() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(ERR_MENU_ITEM_DTO_PRICE_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_REQUIRED)));
        }

        @Test
        void testMenuItemDtoInvalidCategoryNull() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(ERR_MENU_ITEM_DTO_CATEGORY_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CATEGORY_REQUIRED)));
        }

        @Test
        void testMenuItemDtoInvalidAllergenIdsNull() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(ERR_MENU_ITEM_DTO_ALLERGEN_IDS_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_LIST_REQUIRED)));
        }

        @Test
        void testMenuItemDtoInvalidImageUrlNull() {
            Set<ConstraintViolation<MenuItemDTO>> violations = validator.validate(ERR_MENU_ITEM_DTO_IMAGE_URL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_REQUIRED)));
        }
    }
}
