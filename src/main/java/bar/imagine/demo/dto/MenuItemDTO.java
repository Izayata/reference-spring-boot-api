package bar.imagine.demo.dto;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_ALLERGEN_LIST_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_CATEGORY_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_ID_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_NAME_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_IMAGE_URL_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;

import java.util.List;

import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.dto.food.FoodNameDTO;
import bar.imagine.demo.dto.food.ImageURLDTO;
import bar.imagine.demo.dto.food.PriceDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    @NotNull(message = ERR_MSG_FOOD_ID_REQUIRED)
    private Long foodId;
    @Valid
    @NotNull(message = ERR_MSG_FOOD_NAME_REQUIRED)
    private FoodNameDTO foodName;
    @Valid
    @NotNull(message = ERR_MSG_PRICE_REQUIRED)
    private PriceDTO price;
    @NotNull(message = ERR_MSG_CATEGORY_REQUIRED)
    private CategoryEnum category;
    @Valid
    @NotNull(message = ERR_MSG_ALLERGEN_LIST_REQUIRED)
    private List<AllergenDTO> allergens;
    @Valid
    @NotNull(message = ERR_MSG_IMAGE_URL_REQUIRED)
    private ImageURLDTO imageUrl;
}
