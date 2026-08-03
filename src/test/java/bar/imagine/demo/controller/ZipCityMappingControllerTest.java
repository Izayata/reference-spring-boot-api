package bar.imagine.demo.controller;

import bar.imagine.demo.config.SecurityConfig;
import bar.imagine.demo.dto.address.ZipCityLookupDTO;
import bar.imagine.demo.service.RedisService;
import bar.imagine.demo.service.UserService;
import bar.imagine.demo.service.ZipCityMappingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ZipCityMappingController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class ZipCityMappingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ZipCityMappingService zipCityMappingService;

    @MockBean
    private UserService userService;

    @MockBean
    private RedisService redisService;

    @Test
    void getCityByZipCode_returns200_withZipCityLookupDto() throws Exception {
        when(zipCityMappingService.getCityByZipCode("1011")).thenReturn(new ZipCityLookupDTO("1011", "Budapest"));

        mockMvc.perform(get("/v1/zip-codes/1011"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.zipCode").value("1011"))
            .andExpect(jsonPath("$.city").value("Budapest"));
    }

    @Test
    void getCityByZipCode_returns404_whenNoMappingExists() throws Exception {
        when(zipCityMappingService.getCityByZipCode("9999"))
            .thenThrow(new NoSuchElementException("No city found for zip code: 9999"));

        mockMvc.perform(get("/v1/zip-codes/9999"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error").value("No city found for zip code: 9999"));
    }

    @Test
    void getCityByZipCode_returns400_whenZipCodeIsMalformed() throws Exception {
        mockMvc.perform(get("/v1/zip-codes/abc"))
            .andExpect(status().isBadRequest());
    }
}
