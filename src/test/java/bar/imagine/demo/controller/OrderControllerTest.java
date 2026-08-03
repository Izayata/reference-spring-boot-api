package bar.imagine.demo.controller;

import bar.imagine.demo.dto.OrderDTO;
import bar.imagine.demo.service.OrderService;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import bar.imagine.demo.config.SecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    @WithMockUser
    void createOrder_returns400_whenBodyHasMissingFields() throws Exception {
        mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .with(csrf()))
            .andExpect(status().isBadRequest());
    }

    @Test
    void createOrder_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .with(csrf()))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    void createGuestOrder_returns400_whenBodyHasMissingFields() throws Exception {
        // Guest checkout is public and CSRF-exempt; body validation still applies (no auth, no csrf() needed)
        mockMvc.perform(post("/v1/orders/guest")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrderById_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/v1/orders/1"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void getOrderById_returns200_withMockUser() throws Exception {
        OrderDTO mockDto = OrderDTO.builder().id(1L).build();
        when(orderService.getOrderById(eq(1L))).thenReturn(mockDto);

        mockMvc.perform(get("/v1/orders/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser
    void getOrderById_returns404_whenOrderBelongsToAnotherUser() throws Exception {
        when(orderService.getOrderById(eq(99L))).thenThrow(new NoSuchElementException("Order not found"));

        mockMvc.perform(get("/v1/orders/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void getOrderById_returns400_whenIdIsNotANumber() throws Exception {
        mockMvc.perform(get("/v1/orders/abc"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getOrdersForAuthenticatedCustomer_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/v1/orders"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void getOrdersForAuthenticatedCustomer_returns200_withOrderList() throws Exception {
        OrderDTO mockDto = OrderDTO.builder().id(1L).build();
        when(orderService.getOrdersForAuthenticatedCustomer()).thenReturn(List.of(mockDto));

        mockMvc.perform(get("/v1/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1));
    }

    @Test
    @WithMockUser
    void getOrdersForAuthenticatedCustomer_returns200_withEmptyArray_whenNoOrders() throws Exception {
        when(orderService.getOrdersForAuthenticatedCustomer()).thenReturn(List.of());

        mockMvc.perform(get("/v1/orders"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }
}
