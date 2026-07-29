package bar.imagine.demo.converter;

import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.dto.FoodDetailsDTO;
import bar.imagine.demo.dto.MenuItemDTO;
import bar.imagine.demo.dto.ShoppingCartItemDTO;
import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.dto.food.DescriptionDTO;
import bar.imagine.demo.dto.food.FoodNameDTO;
import bar.imagine.demo.dto.food.ImageURLDTO;
import bar.imagine.demo.dto.food.PriceDTO;
import bar.imagine.demo.dto.food.allergen.AllergenNameDTO;
import bar.imagine.demo.dto.food.ingredient.IngredientNameDTO;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class FoodConverter {
    public FoodDetailsDTO convertFoodToFoodDetailsDto(Food food) {
        return FoodDetailsDTO.builder()
            .foodName(new FoodNameDTO(food.getFoodName().getValue()))
            .price(toPriceDTO(food.getPrice()))
            .description(new DescriptionDTO(food.getDescription().getValue()))
            .imageUrl(new ImageURLDTO(food.getImageURL().getValue()))
            .ingredientNames(
                food.getIngredients().stream()
                    .map(ingredient -> new IngredientNameDTO(ingredient.getName().getValue()))
                    .toList()
            )
            .allergens(toAllergenDtoList(food.getAllergens()))
            .build();
    }

    public MenuItemDTO convertFoodToMenuItemDto(Food food) {
        return MenuItemDTO.builder()
            .foodId(food.getId())
            .foodName(
                new FoodNameDTO(food.getFoodName().getValue())
            )
            .price(toPriceDTO(food.getPrice()))
            .category(
                food.getCategory()
            )
            .allergens(toAllergenDtoList(food.getAllergens()))
            .imageUrl(new ImageURLDTO(food.getImageURL().getValue()))
            .build();
    }

    public ShoppingCartItemDTO convertFoodToShoppingCartItemDto(Food food) {
        return ShoppingCartItemDTO.builder()
            .foodId(food.getId())
            .foodName(
                new FoodNameDTO(food.getFoodName().getValue())
            )
            .price(toPriceDTO(food.getPrice()))
            .imageUrl(new ImageURLDTO(food.getImageURL().getValue()))
            .build();
    }

    private PriceDTO toPriceDTO(Price price) {
        return price != null ? PriceDTO.builder()
            .amount(price.getAmount())
            .currency(price.getCurrency())
            .build() : null;
    }

    private List<AllergenDTO> toAllergenDtoList(Collection<Allergen> allergens) {
        return allergens.stream()
            .map(a -> AllergenDTO.builder()
                .id(a.getId())
                .name(new AllergenNameDTO(a.getName().getValue()))
                .iconName(a.getIconName())
                .build())
            .toList();
    }
}
