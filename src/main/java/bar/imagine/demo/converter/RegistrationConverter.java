package bar.imagine.demo.converter;

import bar.imagine.demo.data.Customer;
import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.dto.RegistrationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationConverter {
    private final CustomerConverter customerConverter;

    public Customer convertRegistrationDtoToCustomer(RegistrationDTO registrationDto) {
        CustomerDTO customerDto = CustomerDTO.builder()
            .personalDetails(registrationDto.getPersonalDetails())
            .email(registrationDto.getMyUser().getEmail())
            .shippingAddress(registrationDto.getShippingAddress())
            .billingAddress(registrationDto.getBillingAddress())
            .build();

        return customerConverter.convertCustomerDtoToCustomer(customerDto);
    }
}
