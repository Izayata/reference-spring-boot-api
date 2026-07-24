package bar.imagine.demo.service;

import bar.imagine.demo.converter.FoodConverter;
import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.data.food.FoodName;
import bar.imagine.demo.data.food.Ingredient;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.data.food.allergen.AllergenName;
import bar.imagine.demo.data.food.ingredient.IngredientName;
import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.dto.FoodDTO;
import bar.imagine.demo.dto.FoodDetailsDTO;
import bar.imagine.demo.dto.MenuItemDTO;
import bar.imagine.demo.dto.ShoppingCartItemDTO;
import bar.imagine.demo.dto.food.FoodNameDTO;
import bar.imagine.demo.dto.food.PriceDTO;
import bar.imagine.demo.repository.AllergenRepository;
import bar.imagine.demo.repository.FoodRepository;
import bar.imagine.demo.repository.IngredientRepository;
import bar.imagine.demo.request.data.ShoppingCartRequestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {

    @Mock private FoodRepository foodRepository;
    @Mock private FoodConverter foodConverter;
    @Mock private AllergenRepository allergenRepository;
    @Mock private IngredientRepository ingredientRepository;

    @InjectMocks
    private FoodService foodService;

    private Food buildFood(Long id) {
        return Food.builder()
            .id(id)
            .foodName(new FoodName("Gulyásleves"))
            .price(new Price(new BigDecimal("1200"), CurrencyEnum.HUF))
            .placeToBuy(PlaceToBuyEnum.RESTAURANT)
            .category(CategoryEnum.SOUPS)
            .build();
    }

    @Test
    void getAllFoods_mapsFoodsToMenuItemDtos() {
        Food food = buildFood(1L);
        MenuItemDTO dto = MenuItemDTO.builder().build();
        when(foodRepository.findAllWithAllergens()).thenReturn(List.of(food));
        when(foodConverter.convertFoodToMenuItemDto(food)).thenReturn(dto);

        List<MenuItemDTO> result = foodService.getAllFoods();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }

    @Test
    void getFoodDetailsById_returnsDto_whenFound() {
        Food food = buildFood(1L);
        FoodDetailsDTO dto = FoodDetailsDTO.builder().build();
        when(foodRepository.findByIdWithAllRelations(1L)).thenReturn(Optional.of(food));
        when(foodConverter.convertFoodToFoodDetailsDto(food)).thenReturn(dto);

        assertSame(dto, foodService.getFoodDetailsById(1L));
    }

    @Test
    void getFoodDetailsById_throws_whenNotFound() {
        when(foodRepository.findByIdWithAllRelations(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> foodService.getFoodDetailsById(99L));
    }

    @Test
    void getMenuItemsByPlaceToBuy_mapsFoodsToMenuItemDtos() {
        Food food = buildFood(1L);
        MenuItemDTO dto = MenuItemDTO.builder().build();
        when(foodRepository.findByPlaceToBuyWithAllergens(PlaceToBuyEnum.RESTAURANT)).thenReturn(List.of(food));
        when(foodConverter.convertFoodToMenuItemDto(food)).thenReturn(dto);

        List<MenuItemDTO> result = foodService.getMenuItemsByPlaceToBuy(PlaceToBuyEnum.RESTAURANT);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }

    @Test
    void getFoodById_returnsEntity_whenFound() {
        Food food = buildFood(1L);
        when(foodRepository.findById(1L)).thenReturn(Optional.of(food));

        assertSame(food, foodService.getFoodById(1L));
    }

    @Test
    void getFoodById_throws_whenNotFound() {
        when(foodRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> foodService.getFoodById(99L));
    }

    @Test
    void createFood_buildsEntityFromDto_resolvesAllergensAndIngredients_andReturnsReloadedDetails() {
        Allergen allergen = Allergen.builder().id(1L).name(new AllergenName("Gluten")).iconName("fa-wheat").build();
        Ingredient ingredient = Ingredient.builder().id(2L).name(new IngredientName("Cheese")).build();

        FoodDTO dto = FoodDTO.builder()
            .foodName(new FoodNameDTO("Gulyásleves"))
            .price(new PriceDTO(new BigDecimal("1200"), CurrencyEnum.HUF))
            .placeToBuy(PlaceToBuyEnum.RESTAURANT)
            .category(CategoryEnum.SOUPS)
            .allergens(List.of(bar.imagine.demo.dto.food.AllergenDTO.builder()
                .id(1L)
                .name(new bar.imagine.demo.dto.food.allergen.AllergenNameDTO("Gluten"))
                .build()))
            .ingredients(List.of(bar.imagine.demo.dto.food.IngredientDTO.builder()
                .id(2L)
                .name(new bar.imagine.demo.dto.food.ingredient.IngredientNameDTO("Cheese"))
                .build()))
            .build();

        Food saved = buildFood(5L);
        FoodDetailsDTO expected = FoodDetailsDTO.builder().build();

        when(allergenRepository.getReferenceById(1L)).thenReturn(allergen);
        when(ingredientRepository.getReferenceById(2L)).thenReturn(ingredient);
        when(foodRepository.save(org.mockito.ArgumentMatchers.any(Food.class))).thenReturn(saved);
        when(foodRepository.findByIdWithAllRelations(5L)).thenReturn(Optional.of(saved));
        when(foodConverter.convertFoodToFoodDetailsDto(saved)).thenReturn(expected);

        FoodDetailsDTO result = foodService.createFood(dto);

        assertSame(expected, result);
    }

    @Test
    void getShoppingCartItems_mapsFoodsToShoppingCartItemDtos() {
        Food food = buildFood(1L);
        ShoppingCartItemDTO dto = ShoppingCartItemDTO.builder().build();
        ShoppingCartRequestData requestData = new ShoppingCartRequestData();
        requestData.setIds(List.of(1L));

        when(foodRepository.findAllById(List.of(1L))).thenReturn(List.of(food));
        when(foodConverter.convertFoodToShoppingCartItemDto(food)).thenReturn(dto);

        List<ShoppingCartItemDTO> result = foodService.getShoppingCartItems(requestData);

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }
}
