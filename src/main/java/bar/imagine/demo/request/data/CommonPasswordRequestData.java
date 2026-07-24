package bar.imagine.demo.request.data;

import static bar.imagine.demo.util.request.data.CommonPasswordRequestDataUtils.ERR_MSG_PASSWORD_REQUIRED;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CommonPasswordRequestData {
    @NotBlank(message = ERR_MSG_PASSWORD_REQUIRED)
    private String password;
}
