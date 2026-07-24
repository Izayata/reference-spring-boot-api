package bar.imagine.demo.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NotEmptyListValidatorTest {

    private final NotEmptyListValidator validator = new NotEmptyListValidator();

    @Test
    void isValid_returnsFalse_forNullList() {
        assertFalse(validator.isValid(null, null));
    }

    @Test
    void isValid_returnsFalse_forEmptyList() {
        assertFalse(validator.isValid(List.of(), null));
    }

    @Test
    void isValid_returnsTrue_forNonEmptyList() {
        assertTrue(validator.isValid(List.of("item"), null));
    }
}
