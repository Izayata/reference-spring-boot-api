package bar.imagine.demo.controller;

import bar.imagine.demo.dto.FoodDetailsDTO;
import bar.imagine.demo.service.FoodService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FoodController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FoodService foodService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void getAllFoods_returns200() throws Exception {
        when(foodService.getAllFoods()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/v1/foods"))
            .andExpect(status().isOk());
    }

    @Test
    void createFood_returns3xx_whenUnauthenticated() throws Exception {
        mockMvc.perform(post("/v1/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .with(csrf()))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    void createFood_returns400_withAuthAndInvalidBody() throws Exception {
        mockMvc.perform(post("/v1/foods")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}")
                .with(csrf()))
            .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser
    void createFood_returns201_withValidBody() throws Exception {
        when(foodService.createFood(any())).thenReturn(FoodDetailsDTO.builder().build());

        mockMvc.perform(post("/v1/foods")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"foodName\":{\"value\":\"Test Food\"},\"price\":{\"amount\":10.00,\"currency\":\"HUF\"},\"placeToBuy\":\"RESTAURANT\",\"category\":\"MAIN_DISHES\"}"))
            .andExpect(status().isCreated());
    }
}
