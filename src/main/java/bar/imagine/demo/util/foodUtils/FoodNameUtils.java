package bar.imagine.demo.util.foodUtils;

public class FoodNameUtils {
    public static final int FOOD_NAME_VALUE_MIN_LENGTH = 3;
    public static final int FOOD_NAME_VALUE_MAX_LENGTH = 183;
    public static final String FOOD_NAME_VALUE_ALLOWED_CHARACTERS = "^[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]+( [a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]+)*$";
    public static final String ERR_MSG_FOOD_NAME_VALUE_REQUIRED = "The value of the food name field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_FOOD_NAME_VALUE_LENGTH = "Food name must be at least " + FOOD_NAME_VALUE_MIN_LENGTH + " characters long, but no more than " + FOOD_NAME_VALUE_MAX_LENGTH
        + " characters long!";
    public static final String ERR_MSG_FOOD_NAME_VALUE_INVALID_CHARACTERS = "Food name can only contain letters, spaces and digits and cannot contain any leading or trailing whitespace!";

    private FoodNameUtils() {}
}
