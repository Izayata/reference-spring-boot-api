package bar.imagine.demo.util.request.data.createOrder;

public class OrderItemRequestDataUtils {
    public static final int QUANTITY_MAX_VALUE = 100;

    public static final String ERR_MSG_FOOD_ID_REQUIRED = "Food ID is required!";
    public static final String ERR_MSG_QUANTITY_REQUIRED = "Quantity is required!";
    public static final String ERR_MSG_QUANTITY_TOO_LOW = "Quantity must be at least 1!";
    public static final String ERR_MSG_QUANTITY_TOO_HIGH = "Quantity cannot exceed " + QUANTITY_MAX_VALUE + "!";

    private OrderItemRequestDataUtils() {}
}
