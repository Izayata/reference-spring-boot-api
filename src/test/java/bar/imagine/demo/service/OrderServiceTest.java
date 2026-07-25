package bar.imagine.demo.service;

import bar.imagine.demo.converter.CustomerConverter;
import bar.imagine.demo.converter.OrderConverter;
import bar.imagine.demo.data.Customer;
import bar.imagine.demo.data.MyUser;
import bar.imagine.demo.data.Order;
import bar.imagine.demo.data.myUser.MyUsername;
import bar.imagine.demo.data.order.PaymentType;
import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.dto.EmailDTO;
import bar.imagine.demo.dto.OrderDTO;
import bar.imagine.demo.repository.CustomerRepository;
import bar.imagine.demo.repository.EmailOutboxRepository;
import bar.imagine.demo.repository.OrderRepository;
import bar.imagine.demo.request.data.CreateOrderRequestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock private OrderRepository orderRepository;
    @Mock private FoodService foodService;
    @Mock private UserService userService;
    @Mock private CustomerConverter customerConverter;
    @Mock private EmailService emailService;
    @Mock private OrderConverter orderConverter;
    @Mock private EmailOutboxRepository emailOutboxRepository;
    @Mock private CustomerRepository customerRepository;

    @InjectMocks
    private OrderService orderService;

    private CreateOrderRequestData buildRequest() {
        CreateOrderRequestData request = new CreateOrderRequestData();
        CustomerDTO customerDto = CustomerDTO.builder().email(new EmailDTO("test@example.com")).build();
        request.setCustomer(customerDto);
        request.setOrderItems(List.of());
        request.setPaymentType(PaymentType.CASH);
        return request;
    }

    @Test
    void createOrder_attachesAuthenticatedCustomer_derivedFromSecurityContext() {
        CreateOrderRequestData request = buildRequest();

        Customer customerDetails = mock(Customer.class);
        when(customerConverter.convertCustomerDtoToCustomer(request.getCustomer())).thenReturn(customerDetails);

        Customer authenticatedCustomer = mock(Customer.class);
        MyUser authenticatedUser = mock(MyUser.class);
        when(authenticatedUser.getId()).thenReturn(10L);
        when(authenticatedUser.getMyUsername()).thenReturn(new MyUsername("testuser"));
        when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(customerRepository.findByMyUserId(10L)).thenReturn(Optional.of(authenticatedCustomer));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(emailService.buildOrderConfirmationEmailBody(any(Order.class), eq("testuser"))).thenReturn("body");
        when(orderConverter.convertOrderToOrderDto(any(Order.class))).thenReturn(OrderDTO.builder().id(1L).build());

        OrderDTO result = orderService.createOrder(request);

        assertNotNull(result);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertSame(authenticatedCustomer, orderCaptor.getValue().getCustomer());
    }

    @Test
    void createOrder_throws_whenNoAuthenticatedUser() {
        CreateOrderRequestData request = buildRequest();
        when(userService.getAuthenticatedUser()).thenThrow(new RuntimeException("no authenticated user"));

        assertThrows(RuntimeException.class, () -> orderService.createOrder(request));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createOrder_throws_whenAuthenticatedUserHasNoCustomer() {
        CreateOrderRequestData request = buildRequest();

        MyUser authenticatedUser = mock(MyUser.class);
        when(authenticatedUser.getId()).thenReturn(10L);
        when(userService.getAuthenticatedUser()).thenReturn(authenticatedUser);
        when(customerRepository.findByMyUserId(10L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.createOrder(request));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void createGuestOrder_leavesCustomerNull_andNeverConsultsSecurityContext() {
        CreateOrderRequestData request = buildRequest();

        Customer customerDetails = mock(Customer.class);
        when(customerConverter.convertCustomerDtoToCustomer(request.getCustomer())).thenReturn(customerDetails);

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(emailService.buildOrderConfirmationEmailBody(any(Order.class), isNull())).thenReturn("body");
        when(orderConverter.convertOrderToOrderDto(any(Order.class))).thenReturn(OrderDTO.builder().id(2L).build());

        OrderDTO result = orderService.createGuestOrder(request);

        assertNotNull(result);
        ArgumentCaptor<Order> orderCaptor = ArgumentCaptor.forClass(Order.class);
        verify(orderRepository).save(orderCaptor.capture());
        assertNull(orderCaptor.getValue().getCustomer());
        verify(userService, never()).getAuthenticatedUser();
    }

    @Test
    void getOrderById_throwsNoSuchElement_whenOrderNotFound() {
        when(orderRepository.findByIdWithItemsAndFood(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(99L));
    }

    @Test
    void getOrderById_returnsOrderDto_whenOwnerMatches() {
        Customer orderCustomer = mock(Customer.class);
        when(orderCustomer.getId()).thenReturn(42L);

        Order order = mock(Order.class);
        when(order.getCustomer()).thenReturn(orderCustomer);

        Customer authCustomer = mock(Customer.class);
        when(authCustomer.getId()).thenReturn(42L);

        MyUser authUser = mock(MyUser.class);
        when(authUser.getCustomer()).thenReturn(authCustomer);

        OrderDTO mockDto = OrderDTO.builder().id(1L).build();

        when(orderRepository.findByIdWithItemsAndFood(1L)).thenReturn(Optional.of(order));
        when(userService.getAuthenticatedUser()).thenReturn(authUser);
        when(orderConverter.convertOrderToOrderDto(any(Order.class))).thenReturn(mockDto);

        OrderDTO result = orderService.getOrderById(1L);
        assertNotNull(result);
    }

    @Test
    void getOrderById_throwsNoSuchElement_whenOrderBelongsToOtherUser() {
        Customer orderCustomer = mock(Customer.class);
        when(orderCustomer.getId()).thenReturn(7L);

        Order order = mock(Order.class);
        when(order.getCustomer()).thenReturn(orderCustomer);

        Customer authCustomer = mock(Customer.class);
        when(authCustomer.getId()).thenReturn(99L);

        MyUser authUser = mock(MyUser.class);
        when(authUser.getCustomer()).thenReturn(authCustomer);

        when(orderRepository.findByIdWithItemsAndFood(1L)).thenReturn(Optional.of(order));
        when(userService.getAuthenticatedUser()).thenReturn(authUser);

        assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(1L));
    }

    @Test
    void getOrderById_throwsNoSuchElement_whenOrderIsGuestOrder() {
        Order order = mock(Order.class);
        when(order.getCustomer()).thenReturn(null);

        when(orderRepository.findByIdWithItemsAndFood(1L)).thenReturn(Optional.of(order));

        assertThrows(NoSuchElementException.class, () -> orderService.getOrderById(1L));
    }
}
