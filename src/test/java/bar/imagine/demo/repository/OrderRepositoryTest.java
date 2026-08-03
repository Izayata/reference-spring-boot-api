package bar.imagine.demo.repository;

import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.Email;
import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.Order;
import bar.imagine.demo.data.OrderItem;
import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.data.customer.PersonalDetails;
import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.Street;
import bar.imagine.demo.data.customer.address.StreetNumber;
import bar.imagine.demo.data.customer.address.ZipCode;
import bar.imagine.demo.data.customer.personalDetails.Firstname;
import bar.imagine.demo.data.customer.personalDetails.Lastname;
import bar.imagine.demo.data.customer.personalDetails.PhoneNumber;
import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.data.food.Description;
import bar.imagine.demo.data.food.FoodName;
import bar.imagine.demo.data.food.ImageURL;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.myUser.Password;
import bar.imagine.demo.data.order.PaymentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private MyUserRepository myUserRepository;

    @Autowired
    private FoodRepository foodRepository;

    private Customer buildAndSaveCustomer(String username, String email) {
        MyUser myUser = myUserRepository.save(MyUser.builder()
            .myUsername(new MyUsername(username))
            .password(new Password("testpassword"))
            .email(new Email(email))
            .build());

        Address address = Address.builder()
            .zipCode(new ZipCode("4026"))
            .city(new City("Debrecen"))
            .street(new Street("Petőfi"))
            .streetNumber(new StreetNumber("1"))
            .build();

        return customerRepository.save(Customer.builder()
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
            .build());
    }

    private Food buildAndSaveFood() {
        return foodRepository.save(Food.builder()
            .foodName(new FoodName("Gulyásleves"))
            .price(new Price(new BigDecimal("1200"), CurrencyEnum.HUF))
            .placeToBuy(PlaceToBuyEnum.RESTAURANT)
            .category(CategoryEnum.SOUPS)
            .description(new Description("Finom leves"))
            .imageURL(new ImageURL("https://example.com/soup.jpg"))
            .build());
    }

    private Order buildOrder(Customer customer, Food food) {
        Order order = Order.builder()
            .personalDetails(customer.getPersonalDetails())
            .shippingAddress(customer.getDefaultShippingAddress())
            .totalCost(new Price(new BigDecimal("1200"), CurrencyEnum.HUF))
            .paymentType(PaymentType.CARD)
            .customer(customer)
            .build();

        OrderItem item = OrderItem.builder()
            .food(food)
            .order(order)
            .quantity(1)
            .orderItemPrice(new Price(new BigDecimal("1200"), CurrencyEnum.HUF))
            .build();
        order.setOrderItems(List.of(item));
        return order;
    }

    @Test
    void findByIdWithItemsAndFood_fetchesOrderItemsAndFoodEagerly() {
        Customer customer = buildAndSaveCustomer("customerA", "a@example.com");
        Food food = buildAndSaveFood();
        Order savedOrder = orderRepository.save(buildOrder(customer, food));

        Optional<Order> result = orderRepository.findByIdWithItemsAndFood(savedOrder.getId());

        assertTrue(result.isPresent());
        assertFalse(result.get().getOrderItems().isEmpty());
        assertEquals(food.getId(), result.get().getOrderItems().get(0).getFood().getId());
    }

    @Test
    void findByIdWithItemsAndFood_returnsEmpty_whenNotFound() {
        Optional<Order> result = orderRepository.findByIdWithItemsAndFood(999L);

        assertTrue(result.isEmpty());
    }

    @Test
    void save_populatesCreatedAtAutomatically() {
        Customer customer = buildAndSaveCustomer("customerCreatedAt", "createdat@example.com");
        Food food = buildAndSaveFood();

        Order saved = orderRepository.save(buildOrder(customer, food));

        assertNotNull(saved.getCreatedAt());
    }

    @Test
    void findByCustomerIdWithItemsAndFoodOrderByCreatedAtDesc_returnsOnlyThatCustomersOrders_mostRecentFirst() throws InterruptedException {
        Customer customerA = buildAndSaveCustomer("customerB1", "b1@example.com");
        Customer customerB = buildAndSaveCustomer("customerB2", "b2@example.com");
        Food food = buildAndSaveFood();

        Order older = orderRepository.saveAndFlush(buildOrder(customerA, food));
        Thread.sleep(5);
        Order newer = orderRepository.saveAndFlush(buildOrder(customerA, food));
        orderRepository.saveAndFlush(buildOrder(customerB, food));

        List<Order> result = orderRepository.findByCustomerIdWithItemsAndFoodOrderByCreatedAtDesc(customerA.getId());

        assertEquals(2, result.size());
        assertEquals(newer.getId(), result.get(0).getId());
        assertEquals(older.getId(), result.get(1).getId());
        assertFalse(result.get(0).getOrderItems().isEmpty());
        assertEquals(food.getId(), result.get(0).getOrderItems().get(0).getFood().getId());
    }

    @Test
    void findByCustomerIdWithItemsAndFoodOrderByCreatedAtDesc_excludesGuestOrders() {
        Customer customer = buildAndSaveCustomer("customerB3", "b3@example.com");
        Food food = buildAndSaveFood();
        orderRepository.save(buildOrder(customer, food));

        Order guestOrder = buildOrder(customer, food);
        guestOrder.setCustomer(null);
        orderRepository.save(guestOrder);

        List<Order> result = orderRepository.findByCustomerIdWithItemsAndFoodOrderByCreatedAtDesc(customer.getId());

        assertEquals(1, result.size());
    }

    @Test
    void findByCustomerIdWithItemsAndFoodOrderByCreatedAtDesc_returnsEmptyList_whenCustomerHasNoOrders() {
        Customer customer = buildAndSaveCustomer("customerB4", "b4@example.com");

        List<Order> result = orderRepository.findByCustomerIdWithItemsAndFoodOrderByCreatedAtDesc(customer.getId());

        assertTrue(result.isEmpty());
    }
}
