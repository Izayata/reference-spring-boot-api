package bar.imagine.demo.data.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_LENGTH;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.FIRSTNAME_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.FIRSTNAME_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.FirstnameUtils.FIRSTNAME_MIN_LENGTH;

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
public class Firstname {
    @NotBlank(message = ERR_MSG_FIRSTNAME_VALUE_REQUIRED)
    @Size(min = FIRSTNAME_MIN_LENGTH, max = FIRSTNAME_MAX_LENGTH, message = ERR_MSG_FIRSTNAME_LENGTH)
    @Pattern(regexp = FIRSTNAME_ALLOWED_CHARACTERS, message = ERR_MSG_FIRSTNAME_INVALID_CHARACTERS)
    private final String value;

    @JsonCreator
    public Firstname(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected Firstname() {
        this.value = null;
    }
}
