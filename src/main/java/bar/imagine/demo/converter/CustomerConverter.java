package bar.imagine.demo.converter;

import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerConverter {
    private final PersonalDetailsConverter personalDetailsConverter;
    private final AddressConverter addressConverter;

    public CustomerDTO convertCustomerToCustomerDto(Customer customer) {
        return CustomerDTO.builder()
            .personalDetails(personalDetailsConverter.convertPersonalDetailsToPersonalDetailsDto(customer.getPersonalDetails()))
            .email(new EmailDTO(customer.getEmail().getValue()))
            .shippingAddress(addressConverter.convertAddressToAddressDto(customer.getDefaultShippingAddress()))
            .billingAddress(addressConverter.convertAddressToAddressDto(customer.getBillingAddress()))
            .shippingAddresses(customer.getShippingAddresses() != null
                ? customer.getShippingAddresses().stream()
                    .map(addressConverter::convertAddressToAddressDto)
                    .toList()
                : null)
            .build();
    }

    public Customer convertCustomerDtoToCustomer(CustomerDTO customerDTO) {
        Address shippingAddress = addressConverter.convertAddressDtoToAddress(customerDTO.getShippingAddress());
        Address billingAddress = addressConverter.convertAddressDtoToAddress(customerDTO.getBillingAddress());

        List<Address> shippingAddresses;
        if (customerDTO.getShippingAddresses() != null) {
            shippingAddresses = customerDTO.getShippingAddresses().stream()
                .map(addressConverter::convertAddressDtoToAddress)
                .collect(Collectors.toCollection(ArrayList::new));
        } else {
            shippingAddresses = new ArrayList<>();
            if (shippingAddress != null) {
                shippingAddresses.add(shippingAddress);
            }
        }

        return Customer.builder()
            .personalDetails(personalDetailsConverter.convertPersonalDetailsDtoToPersonalDetails(customerDTO.getPersonalDetails()))
            .email(new Email(customerDTO.getEmail().getValue()))
            .defaultShippingAddress(shippingAddress)
            .billingAddress(billingAddress)
            .shippingAddresses(shippingAddresses)
            .build();
    }
}
