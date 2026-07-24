package bar.imagine.demo.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

// Write-only response object: always built server-side via OrderConverter and returned in HTTP
// responses, never deserialized from client input, so no @NoArgsConstructor/@JsonCreator is needed.
@Getter
@Setter
@Builder
public class OrderItemDTO {
    private FoodSummaryDTO food;

    private int quantity;

    private BigDecimal orderItemPrice;
}
