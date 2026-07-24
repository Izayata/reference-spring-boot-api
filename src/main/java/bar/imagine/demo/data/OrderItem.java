package bar.imagine.demo.data;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.OrderItemUtils.ERR_MSG_QUANTITY_TOO_LOW;

import bar.imagine.demo.data.food.Price;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDER_ITEMS")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FOOD_ID", nullable = false)
    private Food food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Order order;

    @Column(name = "QUANTITY")
    @Min(value = 1, message = ERR_MSG_QUANTITY_TOO_LOW)
    private int quantity;

    @Embedded
    @NotNull(message = ERR_MSG_PRICE_REQUIRED)
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "amount",   column = @Column(name = "ORDER_ITEM_PRICE_AMOUNT")),
        @AttributeOverride(name = "currency", column = @Column(name = "ORDER_ITEM_PRICE_CURRENCY"))
    })
    private Price orderItemPrice;
}
