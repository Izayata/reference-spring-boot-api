package bar.imagine.demo.data;

import static bar.imagine.demo.util.customerUtils.AddressUtils.ERR_MSG_ADDRESS_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.OrderUtils.ERR_MSG_ORDER_ITEMS_REQUIRED;
import static bar.imagine.demo.util.OrderUtils.ERR_MSG_PAYMENT_TYPE_REQUIRED;
import static bar.imagine.demo.util.PersonalDetailsUtils.ERR_MSG_PERSONAL_DETAILS_REQUIRED;

import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.data.customer.PersonalDetails;
import bar.imagine.demo.data.food.Price;
import bar.imagine.demo.data.order.PaymentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDERS")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    @NotNull(message = ERR_MSG_PERSONAL_DETAILS_REQUIRED)
    @Valid
    private PersonalDetails personalDetails;

    @Embedded
    @NotNull(message = ERR_MSG_ADDRESS_REQUIRED)
    @Valid
    @AttributeOverrides({
            @AttributeOverride(name = "zipCode.value", column = @Column(name = "SHIPPING_ZIP_CODE", nullable = false)),
            @AttributeOverride(name = "city.value", column = @Column(name = "SHIPPING_CITY", nullable = false)),
            @AttributeOverride(name = "street.value", column = @Column(name = "SHIPPING_STREET", nullable = false)),
            @AttributeOverride(name = "streetNumber.value", column = @Column(name = "SHIPPING_STREET_NUMBER", nullable = false)),
            @AttributeOverride(name = "floorDoor.value", column = @Column(name = "SHIPPING_FLOOR_DOOR", nullable = true))
    })
    private Address shippingAddress;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @NotNull(message = ERR_MSG_ORDER_ITEMS_REQUIRED)
    @Valid
    @JsonManagedReference
    @Builder.Default
    @ToString.Exclude
    private List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    @NotNull(message = ERR_MSG_PRICE_REQUIRED)
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "amount",   column = @Column(name = "TOTAL_COST_AMOUNT")),
        @AttributeOverride(name = "currency", column = @Column(name = "TOTAL_COST_CURRENCY"))
    })
    private Price totalCost;

    @Column
    @Enumerated(EnumType.STRING)
    @NotNull(message = ERR_MSG_PAYMENT_TYPE_REQUIRED)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    @JsonBackReference("customer-orders")
    @ToString.Exclude
    private Customer customer;

    // Nullable: rows created before this column existed have no real creation time and are left
    // null rather than backfilled with a fabricated value; the frontend treats a missing
    // createdAt as "date unknown".
    @CreationTimestamp
    @Column(name = "CREATED_AT", updatable = false)
    private Instant createdAt;
}
