package bar.imagine.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoForbiddenValueValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoForbiddenValue {
    String message() default "Forbidden value!";
    String[] forbidden() default {};
    boolean forbidZeroPrefixedSlash() default false;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}