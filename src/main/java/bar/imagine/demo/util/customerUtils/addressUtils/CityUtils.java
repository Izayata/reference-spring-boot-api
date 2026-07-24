package bar.imagine.demo.util.customerUtils.addressUtils;

public class CityUtils {
    public static final int CITY_MIN_LENGTH = 2;
    public static final int CITY_MAX_LENGTH = 25;
    public static final String CITY_ALLOWED_CHARACTERS = "^[A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\\-'.]+( [A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű\\-'.]+)*$";

    public static final String ERR_MSG_CITY_REQUIRED = "City is required!";
    public static final String ERR_MSG_CITY_VALUE_REQUIRED = "The value of the city field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_CITY_LENGTH = "City name must be at least " + CITY_MIN_LENGTH + " characters long, but no more than " + CITY_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_CITY_INVALID_CHARACTERS = "City name can only contain letters, spaces, hyphens, apostrophes and periods and cannot contain any leading or trailing whitespace!";

    private CityUtils() {}
}
