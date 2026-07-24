package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.STREET_MIN_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class StreetDTO {
    @NotBlank(message = ERR_MSG_STREET_VALUE_REQUIRED)
    @Size(min = STREET_MIN_LENGTH, max = STREET_MAX_LENGTH, message = ERR_MSG_STREET_LENGTH)
    @Pattern(regexp = STREET_ALLOWED_CHARACTERS, message = ERR_MSG_STREET_INVALID_CHARACTERS)
    private String value;

    @JsonCreator
    public StreetDTO(@JsonProperty("value") String value) {
        this.value = value;
    }
}
