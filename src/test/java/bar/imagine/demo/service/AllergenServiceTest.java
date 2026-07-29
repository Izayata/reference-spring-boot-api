package bar.imagine.demo.service;

import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.allergen.AllergenName;
import bar.imagine.demo.dto.food.AllergenDTO;
import bar.imagine.demo.repository.AllergenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AllergenServiceTest {

    @Mock private AllergenRepository allergenRepository;

    @InjectMocks
    private AllergenService allergenService;

    @Test
    void getAllAllergens_mapsEntitiesToDtos() {
        Allergen allergen = Allergen.builder()
            .id(1L)
            .name(new AllergenName("Gluten"))
            .iconName("fa-wheat")
            .build();
        when(allergenRepository.findAll()).thenReturn(List.of(allergen));

        List<AllergenDTO> result = allergenService.getAllAllergens();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Gluten", result.get(0).getName().getValue());
        assertEquals("fa-wheat", result.get(0).getIconName());
    }

    @Test
    void getAllAllergens_returnsEmptyList_whenNoneExist() {
        when(allergenRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(allergenService.getAllAllergens().isEmpty());
    }
}
