package bar.imagine.demo.controller;

import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.service.CommonPasswordService;
import bar.imagine.demo.service.RegistrationService;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import bar.imagine.demo.config.SecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RegistrationService registrationService;

    @MockBean
    private CommonPasswordService commonPasswordService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void isUsernameExist_returns200_withExistsFalse() throws Exception {
        when(registrationService.existsByUsername(any())).thenReturn(false);
        when(redisService.atomicIncrementWithTtlOnFirstWrite(anyString(), any(Duration.class))).thenReturn(1L);

        mockMvc.perform(get("/v1/registration/username/testuser/exists"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.exists").value(false));
    }

    @Test
    void isUsernameExist_returns429_whenRateLimitExceeded() throws Exception {
        when(redisService.atomicIncrementWithTtlOnFirstWrite(anyString(), any(Duration.class))).thenReturn(21L);

        mockMvc.perform(get("/v1/registration/username/testuser/exists"))
            .andExpect(status().is(429));
    }

    @Test
    void isCommonPassword_returns200() throws Exception {
        when(commonPasswordService.isCommonPassword("test")).thenReturn(false);

        mockMvc.perform(post("/v1/registration/common-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"password\": \"test\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.isCommonPassword").value(false));
    }

    @Test
    void isCommonPassword_returns400_whenPasswordMissing() throws Exception {
        mockMvc.perform(post("/v1/registration/common-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void signup_returns400_whenBodyEmpty() throws Exception {
        mockMvc.perform(post("/v1/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void signup_returns400_whenBodyIsMalformedJson() throws Exception {
        mockMvc.perform(post("/v1/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{not valid json"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void commonPassword_returns405_whenMethodNotSupported() throws Exception {
        mockMvc.perform(get("/v1/registration/common-password"))
            .andExpect(status().is(HttpStatus.METHOD_NOT_ALLOWED.value()));
    }

    @Test
    void signup_returns201_withValidBody() throws Exception {
        when(registrationService.register(any())).thenReturn(MyUserDTO.builder().build());

        mockMvc.perform(post("/v1/registration")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                    "\"myUser\":{" +
                    "\"myUsername\":{\"value\":\"testuser\"}," +
                    "\"newPasswordDetails\":{\"newPassword\":{\"value\":\"Password@1234\"},\"confirmNewPassword\":{\"value\":\"Password@1234\"}}," +
                    "\"email\":{\"value\":\"test@example.com\"}" +
                    "}," +
                    "\"personalDetails\":{" +
                    "\"firstname\":{\"value\":\"Test\"}," +
                    "\"lastname\":{\"value\":\"User\"}," +
                    "\"phoneNumber\":{\"value\":\"+36301234567\"}" +
                    "}," +
                    "\"shippingAddress\":{\"zipCode\":{\"value\":\"1234\"},\"city\":{\"value\":\"Budapest\"},\"street\":{\"value\":\"Fo utca\"},\"streetNumber\":{\"value\":\"1\"}}," +
                    "\"billingAddress\":{\"zipCode\":{\"value\":\"1234\"},\"city\":{\"value\":\"Budapest\"},\"street\":{\"value\":\"Fo utca\"},\"streetNumber\":{\"value\":\"1\"}}" +
                    "}"))
            .andExpect(status().isCreated());
    }
}
