package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_ID_REQUIRED;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_NAME_REQUIRED;

import bar.imagine.demo.dto.food.ingredient.IngredientNameDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class IngredientDTO {
    @NotNull(message = ERR_MSG_INGREDIENT_ID_REQUIRED)
    private Long id;

    @Valid
    @NotNull(message = ERR_MSG_INGREDIENT_NAME_REQUIRED)
    private IngredientNameDTO name;

    @JsonCreator
    public IngredientDTO(@JsonProperty("id") Long id, @JsonProperty("name") IngredientNameDTO name) {
        this.id = id;
        this.name = name;
    }
}
