package bar.imagine.demo.controller;

import bar.imagine.demo.dto.TokenValidationResult;
import bar.imagine.demo.exception.exceptions.TokenExpiredException;
import bar.imagine.demo.service.PasswordResetService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordResetController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class PasswordResetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PasswordResetService passwordResetService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void requestPasswordReset_returns200_withValidBody() throws Exception {
        when(passwordResetService.requestPasswordReset(any())).thenReturn("Reset link sent");

        mockMvc.perform(post("/v1/password-reset/request-password-reset-link")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":{\"value\":\"test@example.com\"},\"myUsername\":{\"value\":\"testuser\"}}"))
            .andExpect(status().isOk());
    }

    @Test
    void validateToken_returns200_withToken() throws Exception {
        when(passwordResetService.validateResetToken(anyString()))
            .thenReturn(new TokenValidationResult(true, false, false, "Valid"));

        mockMvc.perform(get("/v1/password-reset/validate").param("token", "somerawtoken"))
            .andExpect(status().isOk());
    }

    @Test
    void validateToken_returns400_whenTokenParamMissing() throws Exception {
        mockMvc.perform(get("/v1/password-reset/validate"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void validateToken_returns410_whenTokenExpired() throws Exception {
        when(passwordResetService.validateResetToken(anyString()))
            .thenThrow(new TokenExpiredException("The reset link has expired!"));

        mockMvc.perform(get("/v1/password-reset/validate").param("token", "somerawtoken"))
            .andExpect(status().is(HttpStatus.GONE.value()));
    }

    @Test
    void setNewPassword_returns200_withValidBody() throws Exception {
        when(passwordResetService.setNewPassword(any())).thenReturn("Password changed");

        mockMvc.perform(post("/v1/password-reset/set-new-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"token\":\"somerawtoken\",\"newPasswordDetails\":{\"newPassword\":{\"value\":\"NewPassword@1\"},\"confirmNewPassword\":{\"value\":\"NewPassword@1\"}}}"))
            .andExpect(status().isOk());
    }
}
