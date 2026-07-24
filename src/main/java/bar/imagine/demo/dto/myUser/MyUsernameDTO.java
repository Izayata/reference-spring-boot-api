package bar.imagine.demo.dto.myUser;

import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_LENGTH;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_VALUE_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.USERNAME_ALLOWED_CHARACTERS;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.USERNAME_MAX_LENGTH;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.USERNAME_MIN_LENGTH;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class MyUsernameDTO {
    @NotBlank(message = ERR_MSG_USERNAME_VALUE_REQUIRED)
    @Size(min = USERNAME_MIN_LENGTH, max = USERNAME_MAX_LENGTH, message = ERR_MSG_USERNAME_LENGTH)
    @Pattern(regexp = USERNAME_ALLOWED_CHARACTERS, message = ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS)
    private String value;

    @JsonCreator
    public MyUsernameDTO(@JsonProperty("value") String value) {
        this.value = value;
    }
}
