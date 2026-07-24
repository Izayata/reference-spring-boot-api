package bar.imagine.demo.util;

public class EmailUtils {
    public static final int EMAIL_MAX_LENGTH = 73;

    public static final String ERR_MSG_EMAIL_REQUIRED = "Email is required!";
    public static final String ERR_MSG_EMAIL_VALUE_REQUIRED = "The value of the email field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_EMAIL_INVALID_FORMAT = "Email address can be up to " + EMAIL_MAX_LENGTH + " characters long, must contain a local part, an '@' symbol and a domain part and cannot contain any whitespace! (Example: test@example.com)";
    public static final String ERR_MSG_EMAIL_ALREADY_EXISTS = "The email address is already taken!";

    private EmailUtils() {}
}
