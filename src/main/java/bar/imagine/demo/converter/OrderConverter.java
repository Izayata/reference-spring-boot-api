package bar.imagine.demo.converter;

import bar.imagine.demo.data.Order;
import bar.imagine.demo.data.OrderItem;
import bar.imagine.demo.dto.FoodSummaryDTO;
import bar.imagine.demo.dto.OrderDTO;
import bar.imagine.demo.dto.OrderItemDTO;
import bar.imagine.demo.dto.food.FoodNameDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {
    private final AddressConverter addressConverter;

    public OrderDTO convertOrderToOrderDto(Order order) {
        return OrderDTO.builder()
            .id(order.getId())
            .orderItems(order.getOrderItems().stream()
                .map(this::convertOrderItemToOrderItemDto)
                .toList())
            .totalCost(order.getTotalCost() != null ? order.getTotalCost().getAmount() : null)
            .totalCostCurrency(order.getTotalCost() != null ? order.getTotalCost().getCurrency() : null)
            .paymentType(order.getPaymentType())
            .customerFirstname(order.getPersonalDetails().getFirstname().getValue())
            .customerLastname(order.getPersonalDetails().getLastname().getValue())
            .customerEmail(order.getCustomer() != null
                ? order.getCustomer().getEmail().getValue() : null)
            .customerAddress(addressConverter.convertAddressToAddressDto(order.getShippingAddress()))
            .customerPhoneNumber(order.getPersonalDetails().getPhoneNumber().getValue())
            .createdAt(order.getCreatedAt())
            .build();
    }

    private OrderItemDTO convertOrderItemToOrderItemDto(OrderItem item) {
        return OrderItemDTO.builder()
            .food(new FoodSummaryDTO(
                item.getFood().getId(),
                new FoodNameDTO(item.getFood().getFoodName().getValue())
            ))
            .quantity(item.getQuantity())
            .orderItemPrice(item.getOrderItemPrice().getAmount())
            .build();
    }
}
