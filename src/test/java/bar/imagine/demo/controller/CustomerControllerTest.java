package bar.imagine.demo.controller;

import bar.imagine.demo.service.CustomerService;
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

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void getAllCustomers_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/v1/customers"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void getAllCustomers_returns200_forAdmin() throws Exception {
        when(customerService.getAllCustomers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/customers"))
            .andExpect(status().isOk());
    }

    @Test
    void updateBillingAddress_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(put("/v1/customer/billing-address")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"zipCode\":{\"value\":\"1234\"},\"city\":{\"value\":\"Budapest\"},\"street\":{\"value\":\"Fo utca\"},\"streetNumber\":{\"value\":\"1\"}}"))
            .andExpect(status().is3xxRedirection());
    }
}
