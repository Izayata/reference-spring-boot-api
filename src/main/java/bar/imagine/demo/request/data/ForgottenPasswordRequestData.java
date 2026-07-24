package bar.imagine.demo.request.data;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_VALUE_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_REQUIRED;

import bar.imagine.demo.dto.EmailDTO;
import bar.imagine.demo.dto.myUser.MyUsernameDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ForgottenPasswordRequestData {
    @Valid
    @NotNull(message = ERR_MSG_EMAIL_VALUE_REQUIRED)
    EmailDTO email;

    @Valid
    @NotNull(message = ERR_MSG_USERNAME_REQUIRED)
    MyUsernameDTO myUsername;

    @JsonCreator
    public ForgottenPasswordRequestData(
        @JsonProperty("email")
        EmailDTO email,
        @JsonProperty("myUsername")
        MyUsernameDTO myUsername
    ) {
        this.email = email;
        this.myUsername = myUsername;
    }
}
