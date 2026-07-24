package bar.imagine.demo.util.foodUtils;

public class ImageURLUtils {
    public static final int IMAGE_URL_VALUE_MAX_LENGTH = 255;
    public static final String ERR_MSG_IMAGE_URL_VALUE_REQUIRED = "The value of the image URL field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_IMAGE_URL_VALUE_LENGTH = "Image URL must not exceed " + IMAGE_URL_VALUE_MAX_LENGTH + " characters!";
    public static final String ERR_MSG_IMAGE_URL_VALUE_INVALID_FORMAT = "Image URL must be a valid URL!";

    private ImageURLUtils() {}
}
