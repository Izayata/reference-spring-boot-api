package bar.imagine.demo.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

/**
 * Functionally equivalent to {@code jakarta.validation.constraints.NotEmpty} on a {@code List}.
 * Kept as a hand-rolled constraint to demonstrate the custom-annotation + validator pattern used
 * throughout this project (see {@link NoForbiddenValue}, {@link ValidPhoneNumber}), rather than
 * relying solely on the standard library annotation for this one case.
 */
@Documented
@Constraint(validatedBy = NotEmptyListValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyList {
    String message() default "List cannot be empty!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
