package bar.imagine.demo.util.customerUtils.addressUtils;

public class FloorDoorUtils {
    public static final int FLOOR_DOOR_MIN_LENGTH = 1;
    public static final int FLOOR_DOOR_MAX_LENGTH = 10;
    public static final String FLOOR_DOOR_ALLOWED_FORMAT = "^-?[0-9A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű]+(/[0-9A-Za-zÁÉÍÓÖŐÚÜŰáéíóöőúüű]+)?$";

    public static final String ERR_MSG_FLOOR_DOOR_VALUE_REQUIRED = "The value of the floor/door field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_FLOOR_DOOR_LENGTH = "Floor/Door must be at least " + FLOOR_DOOR_MIN_LENGTH + " characters long, but no more than " + FLOOR_DOOR_MAX_LENGTH + " characters long!";
    public static final String ERR_MSG_FLOOR_DOOR_INVALID_FORMAT = "Floor/Door must be in the format '2/5', '-1/b' or 'fsz/A' and cannot contain any whitespace!";

    private FloorDoorUtils() {}
}
