package bar.imagine.demo.service;

import bar.imagine.demo.data.food.Ingredient;
import bar.imagine.demo.data.food.ingredient.IngredientName;
import bar.imagine.demo.dto.food.IngredientDTO;
import bar.imagine.demo.repository.IngredientRepository;
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
class IngredientServiceTest {

    @Mock private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    @Test
    void getAllIngredients_mapsEntitiesToDtos() {
        Ingredient ingredient = Ingredient.builder()
            .id(1L)
            .name(new IngredientName("Cheese"))
            .build();
        when(ingredientRepository.findAll()).thenReturn(List.of(ingredient));

        List<IngredientDTO> result = ingredientService.getAllIngredients();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Cheese", result.get(0).getName().getValue());
    }

    @Test
    void getAllIngredients_returnsEmptyList_whenNoneExist() {
        when(ingredientRepository.findAll()).thenReturn(Collections.emptyList());

        assertTrue(ingredientService.getAllIngredients().isEmpty());
    }
}
