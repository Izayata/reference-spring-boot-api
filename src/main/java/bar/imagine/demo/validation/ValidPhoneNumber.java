package bar.imagine.demo.validation;

import static bar.imagine.demo.util.customerUtils.PhoneNumberUtils.ERR_MSG_PHONE_NUMBER_VALUE_INVALID;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPhoneNumber {
    String message() default ERR_MSG_PHONE_NUMBER_VALUE_INVALID;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
