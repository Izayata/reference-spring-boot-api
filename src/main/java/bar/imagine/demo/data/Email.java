package bar.imagine.demo.data;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_INVALID_FORMAT;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_VALUE_REQUIRED;

import jakarta.validation.constraints.NotBlank;

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
public class Email {
    @jakarta.validation.constraints.Email(message = ERR_MSG_EMAIL_INVALID_FORMAT)
    @NotBlank(message = ERR_MSG_EMAIL_VALUE_REQUIRED)
    private final String value;

    // Lowercased on construct so email-based lookups/uniqueness checks are case-insensitive.
    @JsonCreator
    public Email(@JsonProperty("value") String value) {
        this.value = value != null ? value.toLowerCase() : null;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected Email() {
        this.value = null;
    }
}
