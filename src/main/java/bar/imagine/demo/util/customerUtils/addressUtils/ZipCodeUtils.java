package bar.imagine.demo.util.customerUtils.addressUtils;

public class ZipCodeUtils {
    public static final int ZIP_CODE_LENGTH = 4;
    public static final String ZIP_CODE_PATTERN = "^\\d{" + ZIP_CODE_LENGTH + "}$";

    public static final String ERR_MSG_ZIP_CODE_REQUIRED = "Zip code is required!";
    public static final String ERR_MSG_ZIP_CODE_VALUE_REQUIRED = "The value of the zip code field cannot be null or an empty string, or consist only of whitespaces!";
    public static final String ERR_MSG_ZIP_CODE_INVALID_FORMAT = "Zip code must be exactly " + ZIP_CODE_LENGTH + " digits and cannot contain any whitespace!";

    private ZipCodeUtils() {}
}
