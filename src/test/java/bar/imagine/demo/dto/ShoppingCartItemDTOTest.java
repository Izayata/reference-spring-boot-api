package bar.imagine.demo.dto;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_ID_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_NAME_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_IMAGE_URL_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.dto.food.FoodNameDTO;
import bar.imagine.demo.dto.food.ImageURLDTO;
import bar.imagine.demo.dto.food.PriceDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class ShoppingCartItemDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final ShoppingCartItemDTO VALID_SHOPPING_CART_ITEM_DTO = ShoppingCartItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("12.99"), CurrencyEnum.USD))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final ShoppingCartItemDTO ERR_SHOPPING_CART_ITEM_DTO_FOOD_ID_NULL = ShoppingCartItemDTO.builder()
        .foodId(null)
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("12.99"), CurrencyEnum.USD))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final ShoppingCartItemDTO ERR_SHOPPING_CART_ITEM_DTO_FOOD_NAME_NULL = ShoppingCartItemDTO.builder()
        .foodId(1L)
        .foodName(null)
        .price(new PriceDTO(new BigDecimal("12.99"), CurrencyEnum.USD))
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final ShoppingCartItemDTO ERR_SHOPPING_CART_ITEM_DTO_PRICE_NULL = ShoppingCartItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Pizza"))
        .price(null)
        .imageUrl(new ImageURLDTO("http://example.com/image.jpg"))
        .build();

    private static final ShoppingCartItemDTO ERR_SHOPPING_CART_ITEM_DTO_IMAGE_URL_NULL = ShoppingCartItemDTO.builder()
        .foodId(1L)
        .foodName(new FoodNameDTO("Pizza"))
        .price(new PriceDTO(new BigDecimal("12.99"), CurrencyEnum.USD))
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
        void testShoppingCartItemDtoValid() {
            Set<ConstraintViolation<ShoppingCartItemDTO>> violations = validator.validate(VALID_SHOPPING_CART_ITEM_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testShoppingCartItemDtoInvalidFoodIdNull() {
            Set<ConstraintViolation<ShoppingCartItemDTO>> violations = validator.validate(ERR_SHOPPING_CART_ITEM_DTO_FOOD_ID_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_ID_REQUIRED)));
        }

        @Test
        void testShoppingCartItemDtoInvalidFoodNameNull() {
            Set<ConstraintViolation<ShoppingCartItemDTO>> violations = validator.validate(ERR_SHOPPING_CART_ITEM_DTO_FOOD_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_FOOD_NAME_REQUIRED)));
        }

        @Test
        void testShoppingCartItemDtoInvalidPriceNull() {
            Set<ConstraintViolation<ShoppingCartItemDTO>> violations = validator.validate(ERR_SHOPPING_CART_ITEM_DTO_PRICE_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_REQUIRED)));
        }

        @Test
        void testShoppingCartItemDtoInvalidImageUrlNull() {
            Set<ConstraintViolation<ShoppingCartItemDTO>> violations = validator.validate(ERR_SHOPPING_CART_ITEM_DTO_IMAGE_URL_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_IMAGE_URL_REQUIRED)));
        }
    }
}
