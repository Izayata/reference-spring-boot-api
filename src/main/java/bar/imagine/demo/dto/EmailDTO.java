package bar.imagine.demo.dto;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_INVALID_FORMAT;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_VALUE_REQUIRED;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class EmailDTO {
    @jakarta.validation.constraints.Email(message = ERR_MSG_EMAIL_INVALID_FORMAT)
    @NotBlank(message = ERR_MSG_EMAIL_VALUE_REQUIRED)
    private String value;

    // Lowercased on construct so email-based lookups/uniqueness checks are case-insensitive.
    @JsonCreator
    public EmailDTO(@JsonProperty("value") String value) {
        this.value = value != null ? value.toLowerCase() : null;
    }

}
