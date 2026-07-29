package bar.imagine.demo.controller;

import bar.imagine.demo.config.SecurityConfig;
import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.dto.food.allergen.AllergenNameDTO;
import bar.imagine.demo.service.AllergenService;
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

@WebMvcTest(AllergenController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class AllergenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AllergenService allergenService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void getAllAllergens_returns200_withAllergenList() throws Exception {
        AllergenDTO dto = AllergenDTO.builder().id(1L).name(new AllergenNameDTO("Gluten")).iconName("fa-wheat").build();
        when(allergenService.getAllAllergens()).thenReturn(List.of(dto));

        mockMvc.perform(get("/v1/allergens"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name.value").value("Gluten"))
            .andExpect(jsonPath("$[0].iconName").value("fa-wheat"));
    }
}
