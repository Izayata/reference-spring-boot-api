package bar.imagine.demo.util.customerUtils.addressUtils;

public class StreetNumberUtils {
    public static final int STREET_NUMBER_MIN_LENGTH = 1;
    public static final int STREET_NUMBER_MAX_LENGTH = 8;
    public static final String STREET_NUMBER_ALLOWED_FORMAT = "^\\d+[A-Za-z]?(/(\\d+[A-Za-z]?|[A-Za-z]))?$";

    public static final String ERR_MSG_STREET_NUMBER_REQUIRED = "Street number is required!";
    public static final String ERR_MSG_STREET_NUMBER_VALUE_REQUIRED = "The value of the street number field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_STREET_NUMBER_LENGTH = "Street number must be at least " + STREET_NUMBER_MIN_LENGTH + " characters long, but nor more than " + STREET_NUMBER_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_STREET_NUMBER_INVALID_FORMAT = "Street number must be in the format '1', '1/a' or '1/A', '1/2', or '1a' or '1A' and cannot contain any whitespace or special character other than '/'!";
    public static final String ERR_MSG_STREET_NUMBER_CANNOT_BE_ZERO = "Street number cannot be '0' or start with '0/'!";

    private StreetNumberUtils() {}
}
