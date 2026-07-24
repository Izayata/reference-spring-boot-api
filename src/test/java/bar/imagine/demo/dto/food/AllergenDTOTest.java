package bar.imagine.demo.dto.food;

import static bar.imagine.demo.dto.food.allergen.AllergenNameDTOTest.VALID_ALLERGEN_NAME_DTO;
import static bar.imagine.demo.util.foodUtils.AllergenUtils.ERR_MSG_ALLERGEN_ID_REQUIRED;
import static bar.imagine.demo.util.foodUtils.AllergenUtils.ERR_MSG_ALLERGEN_NAME_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class AllergenDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final AllergenDTO ERR_ALLERGEN_DTO_ID_NULL = AllergenDTO.builder()
        .id(null)
        .name(VALID_ALLERGEN_NAME_DTO)
        .build();
    private static final AllergenDTO ERR_ALLERGEN_DTO_NAME_NULL = AllergenDTO.builder()
        .id(1L)
        .name(null)
        .build();
    private static final AllergenDTO ERR_ALLERGEN_DTO_ID_AND_NAME_NULL = AllergenDTO.builder()
        .id(null)
        .name(null)
        .build();
    private static final AllergenDTO VALID_ALLERGEN_DTO = AllergenDTO.builder()
        .id(1L)
        .name(VALID_ALLERGEN_NAME_DTO)
        .build();

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }


    @Nested
    @DisplayName("Valid")
    class Valid {

        @Test
        void testAllergenDtoValid() {
            Set<ConstraintViolation<AllergenDTO>> violations = validator.validate(VALID_ALLERGEN_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testAllergenDtoInvalidIdNull() {
            Set<ConstraintViolation<AllergenDTO>> violations = validator.validate(ERR_ALLERGEN_DTO_ID_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_ID_REQUIRED)));
        }

        @Test
        void testAllergenDtoInvalidNameNull() {
            Set<ConstraintViolation<AllergenDTO>> violations = validator.validate(ERR_ALLERGEN_DTO_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_REQUIRED)));
        }

        @Test
        void testAllergenDtoInvalidIdAndNameNull() {
            Set<ConstraintViolation<AllergenDTO>> violations = validator.validate(ERR_ALLERGEN_DTO_ID_AND_NAME_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_ID_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ALLERGEN_NAME_REQUIRED)));
        }
    }
}
