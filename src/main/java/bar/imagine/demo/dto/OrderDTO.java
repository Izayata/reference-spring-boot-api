package bar.imagine.demo.dto;

import bar.imagine.demo.data.food.price.CurrencyEnum;
import bar.imagine.demo.data.order.PaymentType;
import bar.imagine.demo.dto.customer.AddressDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

// Write-only response object: always built server-side via OrderConverter and returned in HTTP
// responses, never deserialized from client input, so no @NoArgsConstructor/@JsonCreator is needed.
// CurrencyEnum/PaymentType are shared domain vocabulary (no persistence-specific behavior), used
// directly here rather than mirrored as separate DTO-level enums.
@Getter
@Setter
@Builder
public class OrderDTO {
    private Long id;

    private List<OrderItemDTO> orderItems;

    private BigDecimal totalCost;

    private CurrencyEnum totalCostCurrency;

    private PaymentType paymentType;

    private String customerFirstname;

    private String customerLastname;

    private String customerEmail;

    private AddressDTO customerAddress;

    private String customerPhoneNumber;
}
