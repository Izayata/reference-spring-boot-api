package bar.imagine.demo.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NoForbiddenValueValidatorTest {

    private NoForbiddenValueValidator buildValidator(String[] forbidden, boolean forbidZeroPrefixedSlash) {
        NoForbiddenValueValidator validator = new NoForbiddenValueValidator();
        NoForbiddenValue annotation = new NoForbiddenValue() {
            @Override
            public Class<? extends java.lang.annotation.Annotation> annotationType() {
                return NoForbiddenValue.class;
            }

            @Override
            public String message() {
                return "Forbidden value!";
            }

            @Override
            public String[] forbidden() {
                return forbidden;
            }

            @Override
            public boolean forbidZeroPrefixedSlash() {
                return forbidZeroPrefixedSlash;
            }

            @Override
            public Class<?>[] groups() {
                return new Class<?>[0];
            }

            @Override
            public Class<? extends jakarta.validation.Payload>[] payload() {
                return new Class[0];
            }
        };
        validator.initialize(annotation);
        return validator;
    }

    @Test
    void isValid_returnsTrue_forNullValue() {
        NoForbiddenValueValidator validator = buildValidator(new String[]{"admin"}, false);
        assertTrue(validator.isValid(null, null));
    }

    @Test
    void isValid_returnsFalse_forForbiddenValue() {
        NoForbiddenValueValidator validator = buildValidator(new String[]{"admin"}, false);
        assertFalse(validator.isValid("admin", null));
    }

    @Test
    void isValid_returnsTrue_forNonForbiddenValue() {
        NoForbiddenValueValidator validator = buildValidator(new String[]{"admin"}, false);
        assertTrue(validator.isValid("someone", null));
    }

    @Test
    void isValid_returnsFalse_forZeroPrefixedSlash_whenFlagEnabled() {
        NoForbiddenValueValidator validator = buildValidator(new String[0], true);
        assertFalse(validator.isValid("0/1", null));
    }

    @Test
    void isValid_returnsTrue_forZeroPrefixedSlash_whenFlagDisabled() {
        NoForbiddenValueValidator validator = buildValidator(new String[0], false);
        assertTrue(validator.isValid("0/1", null));
    }
}
