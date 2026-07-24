package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.AllergenUtils.ERR_MSG_ALLERGEN_ID_REQUIRED;
import static bar.imagine.demo.util.foodUtils.AllergenUtils.ERR_MSG_ALLERGEN_NAME_REQUIRED;

import bar.imagine.demo.dto.food.allergen.AllergenNameDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AllergenDTO {
    @NotNull(message = ERR_MSG_ALLERGEN_ID_REQUIRED)
    private Long id;

    @Valid
    @NotNull(message = ERR_MSG_ALLERGEN_NAME_REQUIRED)
    private AllergenNameDTO name;

    @JsonCreator
    public AllergenDTO(@JsonProperty("id") Long id, @JsonProperty("name") AllergenNameDTO name) {
        this.id = id;
        this.name = name;
    }
}
