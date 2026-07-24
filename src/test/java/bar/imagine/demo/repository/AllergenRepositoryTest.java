package bar.imagine.demo.repository;

import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.allergen.AllergenName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class AllergenRepositoryTest {

    @Autowired
    private AllergenRepository allergenRepository;

    @Test
    void save_persistsAllergen_andFindByIdReturnsIt() {
        Allergen allergen = Allergen.builder()
            .name(new AllergenName("Gluten"))
            .iconName("fa-wheat")
            .build();

        Allergen saved = allergenRepository.save(allergen);

        assertTrue(allergenRepository.findById(saved.getId()).isPresent());
        assertEquals("Gluten", allergenRepository.findById(saved.getId()).get().getName().getValue());
    }
}
