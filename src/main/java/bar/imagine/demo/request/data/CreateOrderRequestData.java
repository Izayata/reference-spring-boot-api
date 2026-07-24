package bar.imagine.demo.request.data;

import static bar.imagine.demo.util.request.data.CreateOrderRequestDataUtils.ERR_MSG_ORDER_ITEMS_EMPTY;
import static bar.imagine.demo.util.request.data.CreateOrderRequestDataUtils.ERR_MSG_PAYMENT_TYPE_REQUIRED;

import bar.imagine.demo.data.order.PaymentType;
import bar.imagine.demo.dto.CustomerDTO;
import bar.imagine.demo.request.data.createOrder.OrderItemRequestData;
import bar.imagine.demo.validation.NotEmptyList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequestData {
    @Valid
    @NotNull
    private CustomerDTO customer;

    @Valid
    @NotNull
    @NotEmptyList(message = ERR_MSG_ORDER_ITEMS_EMPTY)
    private List<OrderItemRequestData> orderItems;

    @NotNull(message = ERR_MSG_PAYMENT_TYPE_REQUIRED)
    private PaymentType paymentType;
}
