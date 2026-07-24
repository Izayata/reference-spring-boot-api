package bar.imagine.demo.validation;

import bar.imagine.demo.dto.NewPasswordDetailsDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewPasswordMatchesConfirmNewPasswordValidator implements ConstraintValidator<NewPasswordMatchesConfirmNewPassword, NewPasswordDetailsDTO> {
    @Override
    public boolean isValid(NewPasswordDetailsDTO dto, ConstraintValidatorContext context) {
        if (dto.getNewPassword() == null || dto.getConfirmNewPassword() == null) {
            return true; // Let @NotNull handle null checks
        }
        return dto.getNewPassword().getValue().equals(dto.getConfirmNewPassword().getValue());
    }
}
