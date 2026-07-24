package bar.imagine.demo.service;

import bar.imagine.demo.converter.FoodConverter;
import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.Description;
import bar.imagine.demo.data.food.FoodName;
import bar.imagine.demo.data.food.ImageURL;
import bar.imagine.demo.data.food.Ingredient;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.dto.FoodDetailsDTO;
import bar.imagine.demo.dto.FoodDTO;
import bar.imagine.demo.dto.MenuItemDTO;
import bar.imagine.demo.dto.ShoppingCartItemDTO;
import bar.imagine.demo.repository.AllergenRepository;
import bar.imagine.demo.repository.FoodRepository;
import bar.imagine.demo.repository.IngredientRepository;
import bar.imagine.demo.request.data.ShoppingCartRequestData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodConverter foodConverter;
    private final AllergenRepository allergenRepository;
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<MenuItemDTO> getAllFoods() {
        return foodRepository.findAllWithAllergens().stream()
            .map(foodConverter::convertFoodToMenuItemDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public FoodDetailsDTO getFoodDetailsById(Long id) {
        Food food = foodRepository.findByIdWithAllRelations(id)
            .orElseThrow(() -> new IllegalArgumentException("Food not found with id: " + id));
        return foodConverter.convertFoodToFoodDetailsDto(food);
    }

    @Transactional(readOnly = true)
    public List<MenuItemDTO> getMenuItemsByPlaceToBuy(PlaceToBuyEnum placeToBuy) {
        return foodRepository.findByPlaceToBuyWithAllergens(placeToBuy).stream()
            .map(foodConverter::convertFoodToMenuItemDto)
            .toList();
    }

    @Transactional(readOnly = true)
    public Food getFoodById(Long id) {
        return foodRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Food not found with id: " + id));
    }

    @Transactional
    public FoodDetailsDTO createFood(FoodDTO dto) {
        Set<Allergen> allergens = dto.getAllergens() != null
            ? dto.getAllergens().stream()
                .map(a -> allergenRepository.getReferenceById(a.getId()))
                .collect(Collectors.toSet())
            : new HashSet<>();

        Set<Ingredient> ingredients = dto.getIngredients() != null
            ? dto.getIngredients().stream()
                .map(i -> ingredientRepository.getReferenceById(i.getId()))
                .collect(Collectors.toSet())
            : new HashSet<>();

        Food food = Food.builder()
            .foodName(new FoodName(dto.getFoodName().getValue()))
            .price(Price.builder()
                .amount(dto.getPrice().getAmount())
                .currency(dto.getPrice().getCurrency())
                .build())
            .placeToBuy(dto.getPlaceToBuy())
            .category(dto.getCategory())
            .description(dto.getDescription() != null
                ? new Description(dto.getDescription().getValue()) : null)
            .imageURL(dto.getImageUrl() != null
                ? new ImageURL(dto.getImageUrl().getValue()) : null)
            .allergens(allergens)
            .ingredients(ingredients)
            .build();

        Food saved = foodRepository.save(food);
        return foodConverter.convertFoodToFoodDetailsDto(
            foodRepository.findByIdWithAllRelations(saved.getId()).orElseThrow()
        );
    }

    @Transactional(readOnly = true)
    public List<ShoppingCartItemDTO> getShoppingCartItems(ShoppingCartRequestData requestData) {
        return foodRepository.findAllById(requestData.getIds()).stream()
            .map(foodConverter::convertFoodToShoppingCartItemDto)
            .toList();
    }
}
