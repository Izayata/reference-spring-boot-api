package bar.imagine.demo.controller;

import bar.imagine.demo.dto.MyUserDTO;
import bar.imagine.demo.service.AccountService;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    @WithMockUser
    void getAuthenticatedUser_returns200_withMockUser() throws Exception {
        when(accountService.getAuthenticatedUserProfileAsMyUserDTO()).thenReturn(MyUserDTO.builder().build());

        mockMvc.perform(get("/v1/account/me"))
            .andExpect(status().isOk());
    }

    @Test
    void getAuthenticatedUser_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/v1/account/me"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void updatePassword_returns204_withValidBody() throws Exception {
        mockMvc.perform(patch("/v1/account/password")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"currentPassword\":{\"value\":\"OldPassword@1\"},\"newPasswordDetails\":{\"newPassword\":{\"value\":\"NewPassword@1\"},\"confirmNewPassword\":{\"value\":\"NewPassword@1\"}}}"))
            .andExpect(status().isNoContent());
    }

    @Test
    void updatePassword_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(patch("/v1/account/password")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"currentPassword\":{\"value\":\"OldPassword@1\"},\"newPasswordDetails\":{\"newPassword\":{\"value\":\"NewPassword@1\"},\"confirmNewPassword\":{\"value\":\"NewPassword@1\"}}}"))
            .andExpect(status().is3xxRedirection());
    }
}
