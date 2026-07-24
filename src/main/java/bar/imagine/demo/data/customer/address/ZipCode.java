package bar.imagine.demo.data.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ZIP_CODE_PATTERN;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

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
public class ZipCode {
    @NotBlank(message = ERR_MSG_ZIP_CODE_VALUE_REQUIRED)
    @Pattern(regexp = ZIP_CODE_PATTERN, message = ERR_MSG_ZIP_CODE_INVALID_FORMAT)
    private final String value;

    @JsonCreator
    public ZipCode(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected ZipCode() {
        this.value = null;
    }
}
