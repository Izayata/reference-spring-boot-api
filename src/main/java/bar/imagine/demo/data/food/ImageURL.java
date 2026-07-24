package bar.imagine.demo.data.food;

import static bar.imagine.demo.util.foodUtils.ImageURLUtils.ERR_MSG_IMAGE_URL_VALUE_INVALID_FORMAT;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.ERR_MSG_IMAGE_URL_VALUE_LENGTH;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.ERR_MSG_IMAGE_URL_VALUE_REQUIRED;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.IMAGE_URL_VALUE_MAX_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class ImageURL {
    @NotBlank(message = ERR_MSG_IMAGE_URL_VALUE_REQUIRED)
    @Size(max = IMAGE_URL_VALUE_MAX_LENGTH, message = ERR_MSG_IMAGE_URL_VALUE_LENGTH)
    @URL(message = ERR_MSG_IMAGE_URL_VALUE_INVALID_FORMAT)
    private final String value;

    @JsonCreator
    public ImageURL(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected ImageURL() {
        this.value = null;
    }
}
