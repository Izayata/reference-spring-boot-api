package bar.imagine.demo.dto;

import static bar.imagine.demo.util.MyUserRegistrationUtils.ERR_MSG_MY_USER_REGISTRATION_DTO_REQUIRED;
import static bar.imagine.demo.util.PersonalDetailsUtils.ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED;
import static bar.imagine.demo.util.customerUtils.AddressUtils.ERR_MSG_ADDRESS_REQUIRED;

import bar.imagine.demo.dto.customer.AddressDTO;
import bar.imagine.demo.dto.customer.PersonalDetailsDTO;
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
public class RegistrationDTO {
    @Valid
    @NotNull(message = ERR_MSG_MY_USER_REGISTRATION_DTO_REQUIRED)
    private MyUserRegistrationDTO myUser;
    @Valid
    @NotNull(message = ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED)
    private PersonalDetailsDTO personalDetails;
    @Valid
    @NotNull(message = ERR_MSG_ADDRESS_REQUIRED)
    private AddressDTO shippingAddress;
    @Valid
    @NotNull(message = ERR_MSG_ADDRESS_REQUIRED)
    private AddressDTO billingAddress;

    @JsonCreator
    public RegistrationDTO (
        @JsonProperty("myUser") MyUserRegistrationDTO myUser,
        @JsonProperty("personalDetails") PersonalDetailsDTO personalDetails,
        @JsonProperty("shippingAddress") AddressDTO shippingAddress,
        @JsonProperty("billingAddress") AddressDTO billingAddress
    ) {
        this.myUser = myUser;
        this.personalDetails = personalDetails;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
    }
}
