package bar.imagine.demo.dto;

import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CONFIRM_NEW_PASSWORD_DO_NOT_MATCH_NEW_PASSWORD;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CONFIRM_NEW_PASSWORD_REQUIRED;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_NEW_PASSWORD_REQUIRED;

import bar.imagine.demo.dto.myUser.PasswordDTO;
import bar.imagine.demo.validation.NewPasswordMatchesConfirmNewPassword;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NewPasswordMatchesConfirmNewPassword(message = ERR_MSG_CONFIRM_NEW_PASSWORD_DO_NOT_MATCH_NEW_PASSWORD)
public class NewPasswordDetailsDTO {
    @Valid
    @NotNull(message = ERR_MSG_NEW_PASSWORD_REQUIRED)
    private PasswordDTO newPassword;
    @Valid
    @NotNull(message = ERR_MSG_CONFIRM_NEW_PASSWORD_REQUIRED)
    private PasswordDTO confirmNewPassword;
}
