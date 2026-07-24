package bar.imagine.demo.repository;

import bar.imagine.demo.data.Food;
import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.data.food.Description;
import bar.imagine.demo.data.food.FoodName;
import bar.imagine.demo.data.food.ImageURL;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.data.food.allergen.AllergenName;
import bar.imagine.demo.data.food.price.CurrencyEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class FoodRepositoryTest {

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private AllergenRepository allergenRepository;

    private Food buildFood(String name, PlaceToBuyEnum placeToBuy, Set<Allergen> allergens) {
        return foodRepository.save(Food.builder()
            .foodName(new FoodName(name))
            .price(new Price(new BigDecimal("1200"), CurrencyEnum.HUF))
            .placeToBuy(placeToBuy)
            .category(CategoryEnum.SOUPS)
            .description(new Description("Finom leves"))
            .imageURL(new ImageURL("https://example.com/soup.jpg"))
            .allergens(allergens)
            .build());
    }

    @Test
    void findByPlaceToBuy_returnsOnlyMatchingFoods() {
        buildFood("Gulyásleves", PlaceToBuyEnum.RESTAURANT, Set.of());
        buildFood("Sárkányfarok leves", PlaceToBuyEnum.FANTASY_WORLD, Set.of());

        List<Food> result = foodRepository.findByPlaceToBuy(PlaceToBuyEnum.FANTASY_WORLD);

        assertEquals(1, result.size());
        assertEquals("Sárkányfarok leves", result.get(0).getFoodName().getValue());
    }

    @Test
    void findAllWithAllergens_fetchesAllergensEagerly() {
        Allergen allergen = allergenRepository.save(Allergen.builder()
            .name(new AllergenName("Gluten"))
            .iconName("fa-wheat")
            .build());
        buildFood("Gulyásleves", PlaceToBuyEnum.RESTAURANT, Set.of(allergen));

        List<Food> result = foodRepository.findAllWithAllergens();

        assertEquals(1, result.size());
        assertFalse(result.get(0).getAllergens().isEmpty());
    }

    @Test
    void findByPlaceToBuyWithAllergens_filtersByPlaceToBuy() {
        buildFood("Gulyásleves", PlaceToBuyEnum.RESTAURANT, Set.of());
        buildFood("Sárkányfarok leves", PlaceToBuyEnum.FANTASY_WORLD, Set.of());

        List<Food> result = foodRepository.findByPlaceToBuyWithAllergens(PlaceToBuyEnum.RESTAURANT);

        assertEquals(1, result.size());
        assertEquals("Gulyásleves", result.get(0).getFoodName().getValue());
    }

    @Test
    void findByIdWithAllRelations_returnsFood_whenPresent() {
        Food saved = buildFood("Gulyásleves", PlaceToBuyEnum.RESTAURANT, Set.of());

        Optional<Food> result = foodRepository.findByIdWithAllRelations(saved.getId());

        assertTrue(result.isPresent());
    }

    @Test
    void findByIdWithAllRelations_returnsEmpty_whenAbsent() {
        assertTrue(foodRepository.findByIdWithAllRelations(9999L).isEmpty());
    }
}
