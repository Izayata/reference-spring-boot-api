package bar.imagine.demo.util.foodUtils.ingredient;

public class IngredientUtils {
    public static final int INGREDIENT_VALUE_MIN_LENGTH = 2;
    public static final int INGREDIENT_VALUE_MAX_LENGTH = 254;
    public static final String INGREDIENT_VALUE_ALLOWED_CHARACTERS = "^[a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]+( [a-zA-ZáéíóöőúüűÁÉÍÓÖŐÚÜŰ]+)*$";
    public static final String ERR_MSG_INGREDIENT_VALUE_REQUIRED = "The value of the ingredient field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_INGREDIENT_VALUE_LENGTH = "Ingredient must be at least " + INGREDIENT_VALUE_MIN_LENGTH
        + " characters long, but no more than " + INGREDIENT_VALUE_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_INGREDIENT_VALUE_INVALID_CHARACTERS = "Ingredient can only contain letters and spaces and cannot contain any leading or trailing whitespace!";
    public static final String ERR_MSG_INGREDIENT_NAME_REQUIRED = "Ingredient name is required!";
    public static final String ERR_MSG_INGREDIENT_ID_REQUIRED = "Ingredient ID is required!";

    private IngredientUtils() {}
}
