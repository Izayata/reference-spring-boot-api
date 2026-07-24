package bar.imagine.demo.util.customerUtils;

public class PhoneNumberUtils {
    public static final String PHONE_NUMBER_VALUE_ALLOWED_REGEX = "^\\+?\\d+$";

    public static final String ERR_MSG_PHONE_NUMBER_REQUIRED = "Phone number is required!";
    public static final String ERR_MSG_PHONE_NUMBER_VALUE_REQUIRED = "The value of the phone number field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_PHONE_NUMBER_INVALID_CHARACTER = "The phone number can only contain digits and the + sign!";
    public static final String ERR_MSG_PHONE_NUMBER_VALUE_INVALID = "Invalid phone number!";

    private PhoneNumberUtils() {}
}
