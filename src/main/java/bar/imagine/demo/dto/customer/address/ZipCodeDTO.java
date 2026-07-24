package bar.imagine.demo.dto.customer.address;

import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ZIP_CODE_PATTERN;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Value;

@Value
public class ZipCodeDTO {
    @NotBlank(message = ERR_MSG_ZIP_CODE_VALUE_REQUIRED)
    @Pattern(regexp = ZIP_CODE_PATTERN, message = ERR_MSG_ZIP_CODE_INVALID_FORMAT)
    private String value;

    @JsonCreator
    public ZipCodeDTO(@JsonProperty("value") String value) {
        this.value = value;
    }

}
