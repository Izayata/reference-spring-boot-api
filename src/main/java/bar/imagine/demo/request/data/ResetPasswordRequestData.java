package bar.imagine.demo.request.data;

import static bar.imagine.demo.util.NewPasswordDetailsUtils.ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED;
import static bar.imagine.demo.util.request.data.ResetPasswordRequestDataUtils.ERR_MSG_TOKEN_REQUIRED;

import bar.imagine.demo.dto.NewPasswordDetailsDTO;
import bar.imagine.demo.dto.myUser.PasswordDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequestData {
    @NotBlank(message = ERR_MSG_TOKEN_REQUIRED)
    private String token;

    @Valid
    @NotNull(message = ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED)
    private NewPasswordDetailsDTO newPasswordDetails;
}
