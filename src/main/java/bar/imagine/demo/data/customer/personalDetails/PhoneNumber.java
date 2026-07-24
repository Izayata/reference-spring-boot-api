package bar.imagine.demo.data.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.PHONE_NUMBER_VALUE_ALLOWED_REGEX;

import bar.imagine.demo.validation.ValidPhoneNumber;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Embeddable
public class PhoneNumber {
    @NotBlank(message = ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED)
    @Pattern(regexp = PHONE_NUMBER_VALUE_ALLOWED_REGEX, message = ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER)
    @ValidPhoneNumber
    private final String value;

    @JsonCreator
    public PhoneNumber(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected PhoneNumber() {
        this.value = null;
    }
}
