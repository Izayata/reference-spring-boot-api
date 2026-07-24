package bar.imagine.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class NoForbiddenValueValidator implements ConstraintValidator<NoForbiddenValue, String> {
    private String[] forbidden;
    private boolean forbidZeroPrefixedSlash;

    @Override
    public void initialize(NoForbiddenValue constraintAnnotation) {
        forbidden = constraintAnnotation.forbidden();
        forbidZeroPrefixedSlash = constraintAnnotation.forbidZeroPrefixedSlash();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        if (forbidZeroPrefixedSlash && value.startsWith("0/")) return false;
        return Arrays.stream(forbidden).noneMatch(value::equals);
    }
}