package bar.imagine.demo.repository;

import bar.imagine.demo.data.food.Ingredient;
import bar.imagine.demo.data.food.ingredient.IngredientName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepository;

    @Test
    void save_persistsIngredient_andFindByIdReturnsIt() {
        Ingredient ingredient = Ingredient.builder()
            .name(new IngredientName("Cheese"))
            .build();

        Ingredient saved = ingredientRepository.save(ingredient);

        assertTrue(ingredientRepository.findById(saved.getId()).isPresent());
        assertEquals("Cheese", ingredientRepository.findById(saved.getId()).get().getName().getValue());
    }
}
