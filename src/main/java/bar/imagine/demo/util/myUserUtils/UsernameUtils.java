package bar.imagine.demo.util.myUserUtils;

public class UsernameUtils {
    public static final String USERNAME_ALLOWED_CHARACTERS = "^[a-zA-Z0-9]+$";
    public static final int USERNAME_MIN_LENGTH = 3;
    public static final int USERNAME_MAX_LENGTH = 20;

    public static final String ERR_MSG_USERNAME_REQUIRED = "Username is required!";
    public static final String ERR_MSG_USERNAME_VALUE_REQUIRED = "The value of the username field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_USERNAME_CONTAINS_BANNED_CHARACTERS = "Username can only contain letters from english alphabet and digits!";
    public static final String ERR_MSG_USERNAME_LENGTH = "Username must be at least " + USERNAME_MIN_LENGTH + " characters long, but no more than " + USERNAME_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_USERNAME_IS_TAKEN = "The myUsername is already taken!";

    private UsernameUtils() {}
}
