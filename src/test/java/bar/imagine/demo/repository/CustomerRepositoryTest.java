package bar.imagine.demo.repository;

import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.data.customer.PersonalDetails;
import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.Street;
import bar.imagine.demo.data.customer.address.StreetNumber;
import bar.imagine.demo.data.customer.address.ZipCode;
import bar.imagine.demo.data.customer.personalDetails.Firstname;
import bar.imagine.demo.data.customer.personalDetails.Lastname;
import bar.imagine.demo.data.customer.personalDetails.PhoneNumber;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    private static MyUser buildMyUser(String username, String email) {
        return MyUser.builder()
            .myUsername(new MyUsername(username))
            .password(new Password("testpassword"))
            .email(new Email(email))
            .build();
    }

    private static Customer buildCustomer(String email, MyUser myUser) {
        Address address = Address.builder()
            .zipCode(new ZipCode("4026"))
            .city(new City("Debrecen"))
            .street(new Street("Petőfi"))
            .streetNumber(new StreetNumber("1"))
            .build();

        return Customer.builder()
            .email(new Email(email))
            .personalDetails(PersonalDetails.builder()
                .firstname(new Firstname("Test"))
                .lastname(new Lastname("User"))
                .phoneNumber(new PhoneNumber("+36301234567"))
                .build())
            .billingAddress(address)
            .defaultShippingAddress(address)
            .shippingAddresses(new ArrayList<>())
            .myUser(myUser)
            .build();
    }

    @Test
    void findByEmail_returnsCustomer_whenPresent() {
        MyUser myUser = myUserRepository.save(buildMyUser("testcustomer", "customer@example.com"));
        customerRepository.save(buildCustomer("customer@example.com", myUser));
        Optional<Customer> found = customerRepository.findByEmail(new Email("customer@example.com"));
        assertTrue(found.isPresent());
    }

    @Test
    void findByEmail_returnsEmpty_whenAbsent() {
        Optional<Customer> found = customerRepository.findByEmail(new Email("nobody@example.com"));
        assertFalse(found.isPresent());
    }

    @Test
    void findAllWithShippingAddresses_fetchesShippingAddressesEagerly() {
        MyUser myUser = myUserRepository.save(buildMyUser("shippingcustomer", "shipping@example.com"));
        Customer customer = buildCustomer("shipping@example.com", myUser);
        customer.setShippingAddresses(List.of(customer.getDefaultShippingAddress()));
        customerRepository.save(customer);

        List<Customer> result = customerRepository.findAllWithShippingAddresses();

        assertEquals(1, result.size());
        assertFalse(result.get(0).getShippingAddresses().isEmpty());
    }
}
