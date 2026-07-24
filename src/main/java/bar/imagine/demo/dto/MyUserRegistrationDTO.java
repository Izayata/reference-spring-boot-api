package bar.imagine.demo.dto;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.NewPasswordDetailsUtils.ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_REQUIRED;

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
public class MyUserRegistrationDTO {
    @Valid
    @NotNull(message = ERR_MSG_USERNAME_REQUIRED)
    private MyUsernameDTO myUsername;

    @Valid
    @NotNull(message = ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED)
    private NewPasswordDetailsDTO newPasswordDetails;

    @Valid
    @NotNull(message = ERR_MSG_EMAIL_REQUIRED)
    private EmailDTO email;

    @JsonCreator
    public MyUserRegistrationDTO(
        @JsonProperty("myUsername") MyUsernameDTO myUsername,
        @JsonProperty("newPasswordDetails") NewPasswordDetailsDTO newPasswordDetails,
        @JsonProperty("email") EmailDTO email
    )
    {
        this.myUsername = myUsername;
        this.newPasswordDetails = newPasswordDetails;
        this.email = email;
    }
}
