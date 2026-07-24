package bar.imagine.demo.util.customerUtils;

public class FirstnameUtils {
    public static final int FIRSTNAME_MIN_LENGTH = 2;
    public static final int FIRSTNAME_MAX_LENGTH = 25;
    public static final String FIRSTNAME_ALLOWED_CHARACTERS = "^[A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\\-'.]+( [A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\\-'.]+)*$";

    public static final String ERR_MSG_FIRSTNAME_REQUIRED = "Firstname is required!";
    public static final String ERR_MSG_FIRSTNAME_VALUE_REQUIRED = "The value of the firstname field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_FIRSTNAME_LENGTH = "Firstname must be at least " + FIRSTNAME_MIN_LENGTH + " characters long, but no more than " + FIRSTNAME_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_FIRSTNAME_INVALID_CHARACTERS = "Firstname can only contain letters, spaces, dashes, apostrophes and dots and cannot contain any leading or trailing whitespace!";

    private FirstnameUtils() {}
}
