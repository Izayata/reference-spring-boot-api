package bar.imagine.demo.dto;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_CATEGORY_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_NAME_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PlaceToBuyUtils.ERR_MSG_PLACE_TO_BUY_REQUIRED;

import java.util.List;

import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.dto.food.DescriptionDTO;
import bar.imagine.demo.dto.food.FoodNameDTO;
import bar.imagine.demo.dto.food.ImageURLDTO;
import bar.imagine.demo.dto.food.IngredientDTO;
import bar.imagine.demo.dto.food.PriceDTO;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// CategoryEnum/PlaceToBuyEnum are shared domain vocabulary (no persistence-specific behavior),
// used directly here rather than mirrored as separate DTO-level enums.
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodDTO {
    private Long id;

    @Valid
    @NotNull(message = ERR_MSG_FOOD_NAME_REQUIRED)
    private FoodNameDTO foodName;

    @Valid
    @NotNull(message = ERR_MSG_PRICE_REQUIRED)
    private PriceDTO price;

    @NotNull(message = ERR_MSG_PLACE_TO_BUY_REQUIRED)
    private PlaceToBuyEnum placeToBuy;

    @NotNull(message = ERR_MSG_CATEGORY_REQUIRED)
    private CategoryEnum category;

    @Valid
    @Nullable
    private List<AllergenDTO> allergens;

    @Valid
    @Nullable
    private DescriptionDTO description;

    @Valid
    @Nullable
    private List<IngredientDTO> ingredients;

    @Valid
    @Nullable
    private ImageURLDTO imageUrl;
}
