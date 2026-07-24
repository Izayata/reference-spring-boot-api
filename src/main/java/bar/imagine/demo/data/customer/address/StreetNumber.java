package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.STREET_NUMBER_ALLOWED_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.STREET_NUMBER_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.STREET_NUMBER_MIN_LENGTH;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import bar.imagine.demo.validation.NoForbiddenValue;
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
public class StreetNumber {

    @NotBlank(message = ERR_MSG_STREET_NUMBER_VALUE_REQUIRED)
    @Size(min = STREET_NUMBER_MIN_LENGTH, max = STREET_NUMBER_MAX_LENGTH, message = ERR_MSG_STREET_NUMBER_LENGTH)
    @Pattern(regexp = STREET_NUMBER_ALLOWED_FORMAT, message = ERR_MSG_STREET_NUMBER_INVALID_FORMAT)
    @NoForbiddenValue(forbidden = {"0"}, forbidZeroPrefixedSlash = true, message = ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO)
    private final String value;

    @JsonCreator
    public StreetNumber(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected StreetNumber() {
        this.value = null;
    }
}
