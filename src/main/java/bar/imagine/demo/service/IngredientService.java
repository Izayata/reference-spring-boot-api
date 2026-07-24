package bar.imagine.demo.service;

import java.util.List;

import bar.imagine.demo.dto.food.IngredientDTO;
import bar.imagine.demo.dto.food.ingredient.IngredientNameDTO;
import bar.imagine.demo.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredients() {
        return ingredientRepository.findAll().stream()
            .map(i -> IngredientDTO.builder()
                .id(i.getId())
                .name(new IngredientNameDTO(i.getName().getValue()))
                .build())
            .toList();
    }
}
