package bar.imagine.demo.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        var sanitizedValue = value.replace("\s", "");
        var internationalFormat = sanitizedValue.startsWith("06") ? "+36" + sanitizedValue.substring(2) : sanitizedValue.startsWith("+") ? sanitizedValue : "+" + sanitizedValue;
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            var number = phoneUtil.parse(internationalFormat, null);
            return phoneUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
