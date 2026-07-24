package bar.imagine.demo.dto;

import static bar.imagine.demo.util.NewPasswordDetailsUtils.ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_CURRENT_PASSWORD_REQUIRED;
import static bar.imagine.demo.util.PasswordChangeUtils.ERR_MSG_NEW_PASSWORD_MATCHES_CURRENT_PASSWORD;

import bar.imagine.demo.dto.myUser.PasswordDTO;
import bar.imagine.demo.validation.NewPasswordMatchesCurrentPassword;
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
@NewPasswordMatchesCurrentPassword(message = ERR_MSG_NEW_PASSWORD_MATCHES_CURRENT_PASSWORD)
public class PasswordChangeDTO {
    @Valid
    @NotNull(message = ERR_MSG_CURRENT_PASSWORD_REQUIRED)
    private PasswordDTO currentPassword;

    @Valid
    @NotNull(message = ERR_MSG_NEW_PASSWORD_DETAILS_REQUIRED)
    private NewPasswordDetailsDTO newPasswordDetails;

}
