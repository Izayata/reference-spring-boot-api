package bar.imagine.demo.data.food;

import static bar.imagine.demo.util.foodUtils.DescriptionUtils.DESCRIPTION_VALUE_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.foodUtils.DescriptionUtils.DESCRIPTION_VALUE_MAX_LENGTH;
import static bar.imagine.demo.util.foodUtils.DescriptionUtils.DESCRIPTION_VALUE_MIN_LENGTH;
import static bar.imagine.demo.util.foodUtils.DescriptionUtils.ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS;
import static bar.imagine.demo.util.foodUtils.DescriptionUtils.ERR_MSG_DESCRIPTION_VALUE_LENGTH;
import static bar.imagine.demo.util.foodUtils.DescriptionUtils.ERR_MSG_DESCRIPTION_VALUE_REQUIRED;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class Description {
    @NotBlank(message = ERR_MSG_DESCRIPTION_VALUE_REQUIRED)
    @Size(min = DESCRIPTION_VALUE_MIN_LENGTH, max = DESCRIPTION_VALUE_MAX_LENGTH, message = ERR_MSG_DESCRIPTION_VALUE_LENGTH)
    @Pattern(regexp = DESCRIPTION_VALUE_ALLOWED_CHARACTERS, message = ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS)
    private final String value;

    @JsonCreator
    public Description(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected Description() {
        this.value = null;
    }
}
