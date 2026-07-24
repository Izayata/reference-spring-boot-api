package bar.imagine.demo.controller;

import bar.imagine.demo.config.SecurityConfig;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    @WithMockUser
    void authStatus_returns200_whenAuthenticated() throws Exception {
        mockMvc.perform(get("/auth-status"))
            .andExpect(status().isOk());
    }

    @Test
    void authStatus_returns401_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/auth-status"))
            .andExpect(status().isUnauthorized());
    }
}
