package bar.imagine.demo.dto;

import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.PersonalDetailsUtils.ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED;
import static bar.imagine.demo.util.customerUtils.AddressUtils.ERR_MSG_ADDRESS_REQUIRED;

import bar.imagine.demo.dto.customer.AddressDTO;
import bar.imagine.demo.dto.customer.PersonalDetailsDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @Valid
    @NotNull(message = ERR_MSG_PERSONAL_DETAILS_DTO_REQUIRED)
    private PersonalDetailsDTO personalDetails;

    @Valid
    @NotNull(message = ERR_MSG_EMAIL_REQUIRED)
    private EmailDTO email;

    @Valid
    @NotNull(message = ERR_MSG_ADDRESS_REQUIRED)
    private AddressDTO shippingAddress;

    @Valid
    @NotNull(message = ERR_MSG_ADDRESS_REQUIRED)
    private AddressDTO billingAddress;

    // Intentionally unvalidated: this DTO is dual-purpose. On input (registration/customer-update
    // requests) a client may omit this list entirely, in which case CustomerConverter falls back to
    // a singleton list built from shippingAddress. On output (GET responses) it is always populated.
    // The entity-level @NotEmptyList on Customer.shippingAddresses is what's actually enforced.
    private List<AddressDTO> shippingAddresses;
}
