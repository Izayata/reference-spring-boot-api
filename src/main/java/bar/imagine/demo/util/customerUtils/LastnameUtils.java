package bar.imagine.demo.util.customerUtils;

public class LastnameUtils {
    public static final int LASTNAME_MIN_LENGTH = 2;
    public static final int LASTNAME_MAX_LENGTH = 25;
    public static final String LASTNAME_ALLOWED_CHARACTERS = "^[A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\\-'.]+( [A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\\-'.]+)*$";

    public static final String ERR_MSG_LASTNAME_REQUIRED = "Lastname is required!";
    public static final String ERR_MSG_LASTNAME_VALUE_REQUIRED = "The value of the lastname field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_LASTNAME_LENGTH = "Lastname must be at least " + LASTNAME_MIN_LENGTH + " characters long, but no more than " + LASTNAME_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_LASTNAME_INVALID_CHARACTERS = "Lastname can only contain letters, spaces, dashes, apostrophes and dots and cannot contain any leading or trailing whitespace!";

    private LastnameUtils() {}
}
