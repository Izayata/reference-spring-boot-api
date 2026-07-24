package bar.imagine.demo.dto.customer;

import static bar.imagine.demo.util.customerUtils.FirstnameUtils.ERR_MSG_FIRSTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.LastnameUtils.ERR_MSG_LASTNAME_REQUIRED;
import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_REQUIRED;

import bar.imagine.demo.dto.customer.personalDetails.FirstnameDTO;
import bar.imagine.demo.dto.customer.personalDetails.LastnameDTO;
import bar.imagine.demo.dto.customer.personalDetails.PhoneNumberDTO;
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
public class PersonalDetailsDTO {
    @Valid
    @NotNull(message = ERR_MSG_FIRSTNAME_REQUIRED)
    private FirstnameDTO firstname;

    @Valid
    @NotNull(message = ERR_MSG_LASTNAME_REQUIRED)
    private LastnameDTO lastname;

    @Valid
    @NotNull(message = ERR_MSG_PHONE_NUMBER_REQUIRED)
    private PhoneNumberDTO phoneNumber;
}
