package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.ImageURLUtils.ERR_MSG_IMAGE_URL_VALUE_INVALID_FORMAT;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.ERR_MSG_IMAGE_URL_VALUE_LENGTH;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.ERR_MSG_IMAGE_URL_VALUE_REQUIRED;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.IMAGE_URL_VALUE_MAX_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

@Value
public class ImageURLDTO {
    @NotBlank(message = ERR_MSG_IMAGE_URL_VALUE_REQUIRED)
    @Size(max = IMAGE_URL_VALUE_MAX_LENGTH, message = ERR_MSG_IMAGE_URL_VALUE_LENGTH)
    @URL(message = ERR_MSG_IMAGE_URL_VALUE_INVALID_FORMAT)
    private String value;

    @JsonCreator
    public ImageURLDTO(@JsonProperty("value") String value) {
        this.value = value;
    }
}
