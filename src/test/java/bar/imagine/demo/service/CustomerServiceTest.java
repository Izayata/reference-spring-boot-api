package bar.imagine.demo.service;

import bar.imagine.demo.converter.AddressConverter;
import bar.imagine.demo.converter.CustomerConverter;
import bar.imagine.demo.converter.PersonalDetailsConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.Street;
import bar.imagine.demo.data.customer.address.StreetNumber;
import bar.imagine.demo.data.customer.address.ZipCode;
import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.dto.customer.AddressDTO;
import bar.imagine.demo.dto.customer.PersonalDetailsDTO;
import bar.imagine.demo.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock private CustomerRepository customerRepository;
    @Mock private UserService userService;
    @Mock private PersonalDetailsConverter personalDetailsConverter;
    @Mock private CustomerConverter customerConverter;
    @Mock private AddressConverter addressConverter;

    @InjectMocks
    private CustomerService customerService;

    private Address buildAddress(String zip) {
        return Address.builder()
            .zipCode(new ZipCode(zip))
            .city(new City("Budapest"))
            .street(new Street("Fő utca"))
            .streetNumber(new StreetNumber("1"))
            .build();
    }

    private Customer buildCustomer() {
        return Customer.builder()
            .billingAddress(buildAddress("1011"))
            .defaultShippingAddress(buildAddress("1011"))
            .build();
    }

    @Test
    void getAllCustomers_mapsEntitiesToDtos() {
        Customer customer = buildCustomer();
        CustomerDTO dto = CustomerDTO.builder().build();
        when(customerRepository.findAllWithShippingAddresses()).thenReturn(List.of(customer));
        when(customerConverter.convertCustomerToCustomerDto(customer)).thenReturn(dto);

        List<CustomerDTO> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertSame(dto, result.get(0));
    }

    @Test
    void updateBillingAddress_succeeds_whenCustomerExists() {
        Customer customer = buildCustomer();
        MyUser myUser = MyUser.builder().id(1L).build();
        Address newAddress = buildAddress("2000");
        AddressDTO addressDTO = AddressDTO.builder().build();
        CustomerDTO expectedDto = CustomerDTO.builder().build();

        when(userService.getAuthenticatedUser()).thenReturn(myUser);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.of(customer));
        when(addressConverter.convertAddressDtoToAddress(addressDTO)).thenReturn(newAddress);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerConverter.convertCustomerToCustomerDto(customer)).thenReturn(expectedDto);

        CustomerDTO result = customerService.updateBillingAddress(addressDTO);

        assertSame(expectedDto, result);
        assertEquals(newAddress, customer.getBillingAddress());
    }

    @Test
    void updateBillingAddress_throws_whenCustomerMissing() {
        MyUser myUser = MyUser.builder().id(1L).build();
        when(userService.getAuthenticatedUser()).thenReturn(myUser);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            customerService.updateBillingAddress(AddressDTO.builder().build()));
    }

    @Test
    void updateDefaultShippingAddress_addsNewAddress_toShippingAddressesList() {
        Customer customer = buildCustomer();
        MyUser myUser = MyUser.builder().id(1L).build();
        Address newAddress = buildAddress("3000");
        AddressDTO addressDTO = AddressDTO.builder().build();
        CustomerDTO expectedDto = CustomerDTO.builder().build();

        when(userService.getAuthenticatedUser()).thenReturn(myUser);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.of(customer));
        when(addressConverter.convertAddressDtoToAddress(addressDTO)).thenReturn(newAddress);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerConverter.convertCustomerToCustomerDto(customer)).thenReturn(expectedDto);

        CustomerDTO result = customerService.updateDefaultShippingAddress(addressDTO);

        assertSame(expectedDto, result);
        assertEquals(newAddress, customer.getDefaultShippingAddress());
        assertTrue(customer.getShippingAddresses().contains(newAddress));
    }

    @Test
    void updateDefaultShippingAddress_throws_whenCustomerMissing() {
        MyUser myUser = MyUser.builder().id(1L).build();
        when(userService.getAuthenticatedUser()).thenReturn(myUser);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            customerService.updateDefaultShippingAddress(AddressDTO.builder().build()));
    }

    @Test
    void updatePersonalDetails_succeeds_whenCustomerExists() {
        Customer customer = buildCustomer();
        MyUser myUser = MyUser.builder().id(1L).build();
        PersonalDetailsDTO detailsDTO = PersonalDetailsDTO.builder().build();
        CustomerDTO expectedDto = CustomerDTO.builder().build();

        when(userService.getAuthenticatedUser()).thenReturn(myUser);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.of(customer));
        when(personalDetailsConverter.convertPersonalDetailsDtoToPersonalDetails(detailsDTO)).thenReturn(null);
        when(customerRepository.save(customer)).thenReturn(customer);
        when(customerConverter.convertCustomerToCustomerDto(customer)).thenReturn(expectedDto);

        CustomerDTO result = customerService.updatePersonalDetails(detailsDTO);

        assertSame(expectedDto, result);
    }

    @Test
    void updatePersonalDetails_throws_whenCustomerMissing() {
        MyUser myUser = MyUser.builder().id(1L).build();
        when(userService.getAuthenticatedUser()).thenReturn(myUser);
        when(customerRepository.findByMyUserId(1L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
            customerService.updatePersonalDetails(PersonalDetailsDTO.builder().build()));
    }
}
