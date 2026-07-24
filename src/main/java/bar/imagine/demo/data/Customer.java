package bar.imagine.demo.data;

import static bar.imagine.demo.util.PersonalDetailsUtils.ERR_MSG_PERSONAL_DETAILS_REQUIRED;
import static bar.imagine.demo.util.customerUtils.AddressUtils.ERR_MSG_ADDRESS_REQUIRED;
import static bar.imagine.demo.util.EmailUtils.ERR_MSG_EMAIL_REQUIRED;
import static bar.imagine.demo.util.customerUtils.DefaultShippingAddressUtils.ERR_MSG_DEFAULT_SHIPPING_ADDRESS_REQUIRED;
import static bar.imagine.demo.util.customerUtils.ShippingAddressesUtils.ERR_MSG_SHIPPING_ADDRESSES_EMPTY;
import static bar.imagine.demo.util.customerUtils.ShippingAddressesUtils.ERR_MSG_SHIPPING_ADDRESSES_REQUIRED;

import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.data.customer.PersonalDetails;
import bar.imagine.demo.validation.NotEmptyList;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "CUSTOMERS")
@AttributeOverride(name = "email.value", column = @Column(name = "EMAIL", nullable = false, unique = true))
public class Customer {
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
    @NotNull(message = ERR_MSG_EMAIL_REQUIRED)
    @Valid
    private Email email;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode.value", column = @Column(name = "BILLING_ZIP_CODE", nullable = false)),
        @AttributeOverride(name = "city.value", column = @Column(name = "BILLING_CITY", nullable = false)),
        @AttributeOverride(name = "street.value", column = @Column(name = "BILLING_STREET", nullable = false)),
        @AttributeOverride(name = "streetNumber.value", column = @Column(name = "BILLING_STREET_NUMBER", nullable = false)),
        @AttributeOverride(name = "floorDoor.value", column = @Column(name = "BILLING_FLOOR_DOOR", nullable = true))
    })
    @NotNull(message = ERR_MSG_ADDRESS_REQUIRED)
    @Valid
    private Address billingAddress;

    @ElementCollection
    @CollectionTable(name = "SHIPPING_ADDRESSES", joinColumns = @JoinColumn(name = "CUSTOMER_ID"))
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode.value", column = @Column(name = "ZIP_CODE", nullable = false)),
        @AttributeOverride(name = "city.value", column = @Column(name = "CITY", nullable = false)),
        @AttributeOverride(name = "street.value", column = @Column(name = "STREET", nullable = false)),
        @AttributeOverride(name = "streetNumber.value", column = @Column(name = "STREET_NUMBER", nullable = false)),
        @AttributeOverride(name = "floorDoor.value", column = @Column(name = "FLOOR_DOOR", nullable = true))
    })
    @NotEmptyList(message = ERR_MSG_SHIPPING_ADDRESSES_EMPTY)
    @NotNull(message = ERR_MSG_SHIPPING_ADDRESSES_REQUIRED)
    @Valid
    @Builder.Default
    private List<Address> shippingAddresses = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "zipCode.value", column = @Column(name = "DEFAULT_SHIPPING_ZIP_CODE", nullable = false)),
        @AttributeOverride(name = "city.value", column = @Column(name = "DEFAULT_SHIPPING_CITY", nullable = false)),
        @AttributeOverride(name = "street.value", column = @Column(name = "DEFAULT_SHIPPING_STREET", nullable = false)),
        @AttributeOverride(name = "streetNumber.value", column = @Column(name = "DEFAULT_SHIPPING_STREET_NUMBER", nullable = false)),
        @AttributeOverride(name = "floorDoor.value", column = @Column(name = "DEFAULT_SHIPPING_FLOOR_DOOR", nullable = true))
    })
    @NotNull(message = ERR_MSG_DEFAULT_SHIPPING_ADDRESS_REQUIRED)
    @Valid
    private Address defaultShippingAddress;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @Nullable
    @ToString.Exclude
    @JsonManagedReference("customer-orders")
    @Builder.Default
    private List<Order> orders = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MYUSER_ID", nullable = false)
    @JsonBackReference
    @Nullable
    @ToString.Exclude
    private MyUser myUser;

    @JsonCreator
    public Customer(
        @JsonProperty("personalDetails")
        PersonalDetails personalDetails,
        @JsonProperty("email")
        Email email,
        @JsonProperty("shippingAddress")
        Address defaultShippingAddress,
        @JsonProperty("billingAddress")
        Address billingAddress
    ) {
        this.personalDetails = personalDetails;
        this.email = email;
        this.billingAddress = billingAddress;
        Address resolvedShipping = defaultShippingAddress != null ? defaultShippingAddress : billingAddress;
        this.shippingAddresses = new ArrayList<>();
        this.shippingAddresses.add(resolvedShipping);
        this.defaultShippingAddress = resolvedShipping;
        this.orders = new ArrayList<>();
    }
}