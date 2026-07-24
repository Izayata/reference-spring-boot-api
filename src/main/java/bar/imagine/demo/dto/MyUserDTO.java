package bar.imagine.demo.dto;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.MyUserUtils.ERR_MSG_CUSTOMER_REQUIRED;
import static bar.imagine.demo.util.myUserUtils.UsernameUtils.ERR_MSG_USERNAME_REQUIRED;

import bar.imagine.demo.dto.myUser.MyUsernameDTO;
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
public class MyUserDTO {
    @Valid
    @NotNull(message = ERR_MSG_USERNAME_REQUIRED)
    private MyUsernameDTO myUsername;

    @Valid
    @NotNull(message = ERR_MSG_EMAIL_REQUIRED)
    private EmailDTO email;

    @Valid
    @NotNull(message = ERR_MSG_CUSTOMER_REQUIRED)
    private CustomerDTO customer;
}
