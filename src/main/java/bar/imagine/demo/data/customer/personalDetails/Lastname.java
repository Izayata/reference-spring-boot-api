package bar.imagine.demo.data.customer.personalDetails;

import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_INVALID_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_LENGTH;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_VALUE_REQUIRED;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.LASTNAME_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.LASTNAME_MAX_LENGTH;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.LASTNAME_MIN_LENGTH;

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
public class Lastname {

    @NotBlank(message = ERR_MSG_LASTNAME_VALUE_REQUIRED)
    @Size(min = LASTNAME_MIN_LENGTH, max = LASTNAME_MAX_LENGTH, message = ERR_MSG_LASTNAME_LENGTH)
    @Pattern(regexp = LASTNAME_ALLOWED_CHARACTERS, message = ERR_MSG_LASTNAME_INVALID_CHARACTERS)
    private final String value;

    @JsonCreator
    public Lastname(@JsonProperty("value") String value) {
        this.value = value;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected Lastname() {
        this.value = null;
    }
}
