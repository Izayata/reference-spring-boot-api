package bar.imagine.demo.controller;

import bar.imagine.demo.config.SecurityConfig;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CsrfController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class CsrfControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void getCsrfToken_returns200_withTokenField() throws Exception {
        mockMvc.perform(get("/csrf-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.csrfToken").exists());
    }
}
