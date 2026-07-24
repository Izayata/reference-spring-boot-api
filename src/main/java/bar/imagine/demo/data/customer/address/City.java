package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.CITY_MIN_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_VALUE_REQUIRED;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class City {

    @NotBlank(message = ERR_MSG_CITY_VALUE_REQUIRED)
    @Size(min = CITY_MIN_LENGTH, max = CITY_MAX_LENGTH, message = ERR_MSG_CITY_LENGTH)
    @Pattern(regexp = CITY_ALLOWED_CHARACTERS, message = ERR_MSG_CITY_INVALID_CHARACTERS)
    private final String value;

    @JsonCreator
    public City(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected City() {
        this.value = null;
    }
}
