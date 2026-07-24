package bar.imagine.demo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = NewPasswordMatchesCurrentPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewPasswordMatchesCurrentPassword {
    String message() default "New password must not match current password!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
