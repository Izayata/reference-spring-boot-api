package bar.imagine.demo.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PhoneNumberValidatorTest {

    private final PhoneNumberValidator validator = new PhoneNumberValidator();

    @Test
    void isValid_returnsTrue_forNullValue() {
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void isValid_returnsTrue_forHungarianZeroSixPrefix_normalizedToPlusThirtySix() {
        assertTrue(validator.isValid("06301234567", null));
    }

    @Test
    void isValid_returnsTrue_forAlreadyInternationalFormat() {
        assertTrue(validator.isValid("+36301234567", null));
    }

    @Test
    void isValid_returnsTrue_forNumberWithoutPlusPrefix_treatedAsInternational() {
        assertTrue(validator.isValid("36301234567", null));
    }

    @Test
    void isValid_returnsTrue_forNumberContainingWhitespace() {
        assertTrue(validator.isValid("06 30 123 4567", null));
    }

    @Test
    void isValid_returnsFalse_forMalformedNumber() {
        assertFalse(validator.isValid("not-a-number", null));
    }

    @Test
    void isValid_returnsFalse_forTooShortNumber() {
        assertFalse(validator.isValid("+3612", null));
    }
}
