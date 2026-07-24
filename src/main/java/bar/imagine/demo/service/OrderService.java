package bar.imagine.demo.service;

import bar.imagine.demo.converter.CustomerConverter;
import bar.imagine.demo.converter.OrderConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.Order;
import bar.imagine.demo.data.OrderItem;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.dto.OrderDTO;
import bar.imagine.demo.repository.CustomerRepository;
import bar.imagine.demo.repository.OrderRepository;
import bar.imagine.demo.request.data.CreateOrderRequestData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodService foodService;
    private final UserService userService;
    private final CustomerConverter customerConverter;
    private final OrderConverter orderConverter;
    private final EmailService emailService;
    private final ApplicationEventPublisher eventPublisher;
    private final CustomerRepository customerRepository;

    @Transactional
    public OrderDTO createOrder(CreateOrderRequestData orderRequest) {
        MyUser authenticatedUser = userService.getAuthenticatedUser();
        Customer authenticatedCustomer = customerRepository.findByMyUserId(authenticatedUser.getId())
                .orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return persistOrder(orderRequest, authenticatedCustomer, authenticatedUser.getMyUsername().getValue());
    }

    @Transactional
    public OrderDTO createGuestOrder(CreateOrderRequestData orderRequest) {
        return persistOrder(orderRequest, null, null);
    }

    private OrderDTO persistOrder(CreateOrderRequestData orderRequest, Customer customer, String authenticatedUsername) {
        var orderToSave = convertCreateOrderRequestDataToOrder(orderRequest, customer);
        var recipientEmail = orderRequest.getCustomer().getEmail();

        log.debug("Saving order");
        Order savedOrder = orderRepository.save(orderToSave);

        String body = emailService.buildOrderConfirmationEmailBody(savedOrder, authenticatedUsername);
        eventPublisher.publishEvent(new OrderConfirmedEvent(recipientEmail.getValue(), body));

        return orderConverter.convertOrderToOrderDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findByIdWithItemsAndFood(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        Customer orderCustomer = order.getCustomer();
        if (orderCustomer == null) {
            throw new NoSuchElementException("Order not found");
        }
        MyUser authenticatedUser = userService.getAuthenticatedUser();
        // Safe despite the stale MyUserPrincipal-held Customer proxy: Hibernate resolves
        // .getId() from the FK column without hitting the DB, so no LazyInitializationException.
        if (!orderCustomer.getId().equals(authenticatedUser.getCustomer().getId())) {
            throw new NoSuchElementException("Order not found");
        }
        return orderConverter.convertOrderToOrderDto(order);
    }

    private Order convertCreateOrderRequestDataToOrder(CreateOrderRequestData orderRequest, Customer customer) {
        var orderToSave = new Order();
        log.debug("Building order from request");
        var customerDetails = customerConverter.convertCustomerDtoToCustomer(
                orderRequest.getCustomer()
        );

        var orderItemsToSave = orderRequest.getOrderItems().stream().map(orderItemRequestData -> {
            var food = foodService.getFoodById(orderItemRequestData.getFoodId());
            return OrderItem
                    .builder()
                    .food(food)
                    .quantity(orderItemRequestData.getQuantity())
                    .orderItemPrice(
                            Price.builder()
                                .amount(food.getPrice().getAmount().multiply(
                                    BigDecimal.valueOf(orderItemRequestData.getQuantity())))
                                .currency(food.getPrice().getCurrency())
                                .build()
                    )
                    .build();
        }).toList();
        log.debug("Order items created, count: {}", orderItemsToSave.size());

        orderToSave.setPersonalDetails(customerDetails.getPersonalDetails());
        orderToSave.setShippingAddress(customerDetails.getDefaultShippingAddress());

        orderToSave.setOrderItems(orderItemsToSave);
        orderItemsToSave.forEach(item -> item.setOrder(orderToSave));

        orderToSave.setTotalCost(
                Price
                        .builder()
                        .amount(
                                orderItemsToSave.stream()
                                        .map(item -> item.getOrderItemPrice().getAmount())
                                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        )
                        .currency(CurrencyEnum.HUF)
                        .build()
        );
        log.debug("Total cost computed: {}", orderToSave.getTotalCost().getAmount());
        orderToSave.setPaymentType(orderRequest.getPaymentType());
        orderToSave.setCustomer(customer);
        log.debug(customer != null ? "Linked order to authenticated customer" : "Processing as guest order");

        return orderToSave;
    }
}
