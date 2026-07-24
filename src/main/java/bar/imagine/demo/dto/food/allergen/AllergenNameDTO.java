package bar.imagine.demo.dto.food.allergen;

import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.ALLERGEN_NAME_VALUE_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.ALLERGEN_NAME_VALUE_MAX_LENGTH;
import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.ALLERGEN_NAME_VALUE_MIN_LENGTH;
import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS;
import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.ERR_MSG_ALLERGEN_NAME_LENGTH;
import static bar.imagine.demo.util.foodUtils.allergen.AllergenNameUtils.ERR_MSG_ALLERGEN_NAME_VALUE_REQUIRED;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class AllergenNameDTO {
    @NotBlank(message = ERR_MSG_ALLERGEN_NAME_VALUE_REQUIRED)
    @Size(min = ALLERGEN_NAME_VALUE_MIN_LENGTH, max = ALLERGEN_NAME_VALUE_MAX_LENGTH, message = ERR_MSG_ALLERGEN_NAME_LENGTH)
    @Pattern(regexp = ALLERGEN_NAME_VALUE_ALLOWED_CHARACTERS, message = ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS)
    private String value;

    @JsonCreator
    public AllergenNameDTO(@JsonProperty("value") String value) {
        this.value = value;
    }
}
