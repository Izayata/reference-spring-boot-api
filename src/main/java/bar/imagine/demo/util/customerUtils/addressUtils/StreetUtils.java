package bar.imagine.demo.util.customerUtils.addressUtils;

public class StreetUtils {
    public static final int STREET_MIN_LENGTH = 2;
    public static final int STREET_MAX_LENGTH = 50;
    public static final String STREET_ALLOWED_CHARACTERS = "^[A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű0-9\\-'.]+( [A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű0-9\\-'.]+)*$";

    public static final String ERR_MSG_STREET_REQUIRED = "Street is required!";
    public static final String ERR_MSG_STREET_VALUE_REQUIRED = "The value of the street field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_STREET_LENGTH = "Street name must be at least " + STREET_MIN_LENGTH + " characters long, but no more than " + STREET_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_STREET_INVALID_CHARACTERS = "Street name can only contain letters, numbers, spaces, hyphens, apostrophes and periods and cannot contain any leading or trailing whitespace!";

    private StreetUtils() {}
}
