package bar.imagine.demo.dto.food.ingredient;

import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_VALUE_LENGTH;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.ERR_MSG_INGREDIENT_VALUE_REQUIRED;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.INGREDIENT_VALUE_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.INGREDIENT_VALUE_MAX_LENGTH;
import static bar.imagine.demo.util.foodUtils.ingredient.IngredientUtils.INGREDIENT_VALUE_MIN_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class IngredientNameDTO {
    @NotBlank(message = ERR_MSG_INGREDIENT_VALUE_REQUIRED)
    @Size(min = INGREDIENT_VALUE_MIN_LENGTH, max = INGREDIENT_VALUE_MAX_LENGTH, message = ERR_MSG_INGREDIENT_VALUE_LENGTH)
    @Pattern(regexp = INGREDIENT_VALUE_ALLOWED_CHARACTERS, message = ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS)
    private String value;

    @JsonCreator
    public IngredientNameDTO(@JsonProperty("value") String value) {
        this.value = value;
    }
}
