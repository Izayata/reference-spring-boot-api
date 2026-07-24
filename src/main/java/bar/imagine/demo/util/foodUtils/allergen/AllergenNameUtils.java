package bar.imagine.demo.util.foodUtils.allergen;

public class AllergenNameUtils {
    public static final int ALLERGEN_NAME_VALUE_MIN_LENGTH = 2;
    public static final int ALLERGEN_NAME_VALUE_MAX_LENGTH = 254;
    public static final String ALLERGEN_NAME_VALUE_ALLOWED_CHARACTERS = "^[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]+( [a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]+)*$";
    public static final String ERR_MSG_ALLERGEN_NAME_VALUE_REQUIRED = "The value of the allergen field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_ALLERGEN_NAME_LENGTH = "Allergen must be at least " + ALLERGEN_NAME_VALUE_MIN_LENGTH
        + " characters long, but no more than " + ALLERGEN_NAME_VALUE_MAX_LENGTH
        + " characters long!";
    public static final String ERR_MSG_ALLERGEN_NAME_INVALID_CHARACTERS = "Allergen can only contain letters and spaces and cannot contain any leading or trailing whitespace!";

    private AllergenNameUtils() {}
}
