package bar.imagine.demo.service;

import java.util.List;
import java.util.NoSuchElementException;

import bar.imagine.demo.converter.AddressConverter;
import bar.imagine.demo.converter.CustomerConverter;
import bar.imagine.demo.converter.PersonalDetailsConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.dto.customer.PersonalDetailsDTO;
import bar.imagine.demo.dto.customer.AddressDTO;
import bar.imagine.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final PersonalDetailsConverter personalDetailsConverter;
    private final CustomerConverter customerConverter;
    private final AddressConverter addressConverter;

    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAllWithShippingAddresses().stream()
            .map(customerConverter::convertCustomerToCustomerDto)
            .toList();
    }

    @Transactional
    public CustomerDTO updateBillingAddress(AddressDTO addressDTO) {
        Customer customer = fetchAuthenticatedCustomer();
        customer.setBillingAddress(addressConverter.convertAddressDtoToAddress(addressDTO));
        return customerConverter.convertCustomerToCustomerDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDTO updateDefaultShippingAddress(AddressDTO addressDTO) {
        Customer customer = fetchAuthenticatedCustomer();

        Address defaultShippingAddressToSave = addressConverter.convertAddressDtoToAddress(addressDTO);

        customer.setDefaultShippingAddress(defaultShippingAddressToSave);

        if (!customer.getShippingAddresses().contains(defaultShippingAddressToSave)) {
            customer.getShippingAddresses().add(defaultShippingAddressToSave);
        }

        return customerConverter.convertCustomerToCustomerDto(customerRepository.save(customer));
    }

    @Transactional
    public CustomerDTO updatePersonalDetails(PersonalDetailsDTO personalDetailsDto) {
        Customer customer = fetchAuthenticatedCustomer();

        customer.setPersonalDetails(
            personalDetailsConverter.convertPersonalDetailsDtoToPersonalDetails(personalDetailsDto)
        );

        return customerConverter.convertCustomerToCustomerDto(customerRepository.save(customer));
    }

    // MyUser.getCustomer() is a lazy @OneToOne association loaded on the authenticated principal at
    // login time (see MyUserPrincipal); by the time it's touched in a later request, its persistence
    // context is long closed and any access throws LazyInitializationException. Fetching Customer
    // fresh here, inside this method's own transaction, keeps it attached for the rest of the call.
    private Customer fetchAuthenticatedCustomer() {
        MyUser myUser = userService.getAuthenticatedUser();
        return customerRepository.findByMyUserId(myUser.getId())
            .orElseThrow(() -> new NoSuchElementException("Customer not found for authenticated user"));
    }
}
