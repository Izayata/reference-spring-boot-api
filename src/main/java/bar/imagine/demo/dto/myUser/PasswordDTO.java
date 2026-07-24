package bar.imagine.demo.dto.myUser;

import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_LENGTH;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.ERR_MSG_PASSWORD_VALUE_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.PASSWORD_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.PASSWORD_MAX_LENGTH;
import static bar.imagine.demo.util.myUserUtils.PasswordUtils.PASSWORD_MIN_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class PasswordDTO {
    @NotBlank(message = ERR_MSG_PASSWORD_VALUE_REQUIRED)
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH, message = ERR_MSG_PASSWORD_LENGTH)
    @Pattern(regexp = PASSWORD_ALLOWED_CHARACTERS, message = ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS)
    private String value;

    @JsonCreator
    public PasswordDTO(@JsonProperty("value") String value) {
        this.value = value;
    }
}
