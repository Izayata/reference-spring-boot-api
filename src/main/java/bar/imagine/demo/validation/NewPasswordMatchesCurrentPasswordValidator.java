package bar.imagine.demo.validation;

import bar.imagine.demo.dto.PasswordChangeDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NewPasswordMatchesCurrentPasswordValidator implements ConstraintValidator<NewPasswordMatchesCurrentPassword, PasswordChangeDTO> {
    @Override
    public boolean isValid(PasswordChangeDTO dto, ConstraintValidatorContext context) {
        if (dto.getCurrentPassword() == null || dto.getNewPasswordDetails() == null
                || dto.getNewPasswordDetails().getNewPassword() == null) {
            return true;
        }
        return !(dto.getCurrentPassword().getValue().equals(dto.getNewPasswordDetails().getNewPassword().getValue()));
    }
}
