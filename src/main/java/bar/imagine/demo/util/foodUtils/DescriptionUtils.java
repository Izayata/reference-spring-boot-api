package bar.imagine.demo.util.foodUtils;

public class DescriptionUtils {
    public static final int DESCRIPTION_VALUE_MIN_LENGTH = 3;
    public static final int DESCRIPTION_VALUE_MAX_LENGTH = 1024;
    public static final String DESCRIPTION_VALUE_ALLOWED_CHARACTERS = "^[a-zA-Z0-9#@&*()\\[\\]{}%\\-+=/.,!?;:'\"áéíóöőúüűÁÉÍÓŐÚÜŰ]+( [a-zA-Z0-9#@&*()\\[\\]{}%\\-+=/.,!?;:'\"áéíóöőúüűÁÉÍÓŐÚÜŰ]+)*$";
    public static final String ERR_MSG_DESCRIPTION_VALUE_REQUIRED = "The value of the description field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_DESCRIPTION_VALUE_LENGTH = "Description must be at least " + DESCRIPTION_VALUE_MIN_LENGTH
        + " characters long, but no more than " + DESCRIPTION_VALUE_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_DESCRIPTION_VALUE_INVALID_CHARACTERS = "Description can only contain letters, spaces, digits and commonly used special character and cannot contain any leading or trailing whitespace!";

    private DescriptionUtils() {}
}
