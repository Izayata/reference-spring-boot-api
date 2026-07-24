package bar.imagine.demo.util.myUserUtils;

import bar.imagine.demo.service.CommonPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordUtils {
    private final CommonPasswordService commonPasswordService;

    public static final int PASSWORD_MIN_LENGTH = 8;
    public static final int PASSWORD_MAX_LENGTH = 24;
    public static final String PASSWORD_ALLOWED_CHARACTERS = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[^\\s]+$";

    public static final String ERR_MSG_PASSWORD_REQUIRED = "Password is required!";
    public static final String ERR_MSG_PASSWORD_VALUE_REQUIRED = "The value of the password field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_PASSWORD_LENGTH = "Password must be at least " + PASSWORD_MIN_LENGTH + " characters long, but no more than " + PASSWORD_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_PASSWORD_CONTAINS_BANNED_CHARACTERS = "Password can only contain lowercase and uppercase letters from english alphabet, digits and special characters! Password must contain at least one of each!";
    public static final String ERR_MSG_COMMON_PASSWORD = "The password is too common, choose a different one!";

    public void throwErrorIfCommonPassword(String passwordAsString) {
        if (commonPasswordService.isCommonPassword(passwordAsString)) {
            throw new IllegalArgumentException(ERR_MSG_COMMON_PASSWORD);
        }
    }
}
