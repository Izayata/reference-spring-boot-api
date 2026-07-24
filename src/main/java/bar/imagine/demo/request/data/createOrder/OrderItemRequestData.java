package bar.imagine.demo.request.data.createOrder;

import static bar.imagine.demo.util.request.data.createOrder.OrderItemRequestDataUtils.ERR_MSG_FOOD_ID_REQUIRED;
import static bar.imagine.demo.util.request.data.createOrder.OrderItemRequestDataUtils.ERR_MSG_QUANTITY_REQUIRED;
import static bar.imagine.demo.util.request.data.createOrder.OrderItemRequestDataUtils.ERR_MSG_QUANTITY_TOO_HIGH;
import static bar.imagine.demo.util.request.data.createOrder.OrderItemRequestDataUtils.ERR_MSG_QUANTITY_TOO_LOW;
import static bar.imagine.demo.util.request.data.createOrder.OrderItemRequestDataUtils.QUANTITY_MAX_VALUE;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestData {
    @NotNull(message = ERR_MSG_FOOD_ID_REQUIRED)
    private Long foodId;

    @NotNull(message = ERR_MSG_QUANTITY_REQUIRED)
    @Min(value = 1, message = ERR_MSG_QUANTITY_TOO_LOW)
    @Max(value = QUANTITY_MAX_VALUE, message = ERR_MSG_QUANTITY_TOO_HIGH)
    private Integer quantity;
}
