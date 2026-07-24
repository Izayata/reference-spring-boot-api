package bar.imagine.demo.controller;

import bar.imagine.demo.config.SecurityConfig;
import bar.imagine.demo.dto.food.IngredientDTO;
import bar.imagine.demo.dto.food.ingredient.IngredientNameDTO;
import bar.imagine.demo.service.IngredientService;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(IngredientController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void getAllIngredients_returns200_withIngredientList() throws Exception {
        IngredientDTO dto = IngredientDTO.builder().id(1L).name(new IngredientNameDTO("Cheese")).build();
        when(ingredientService.getAllIngredients()).thenReturn(List.of(dto));

        mockMvc.perform(get("/v1/ingredients"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name.value").value("Cheese"));
    }
}
