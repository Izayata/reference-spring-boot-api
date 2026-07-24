package bar.imagine.demo.data.customer;

import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_REQUIRED;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.FloorDoor;
import bar.imagine.demo.data.customer.address.Street;
import bar.imagine.demo.data.customer.address.StreetNumber;
import bar.imagine.demo.data.customer.address.ZipCode;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
    @NotNull(message = ERR_MSG_ZIP_CODE_REQUIRED)
    @Valid
    @Embedded
    private ZipCode zipCode;

    @NotNull(message = ERR_MSG_CITY_REQUIRED)
    @Valid
    @Embedded
    private City city;

    @NotNull(message = ERR_MSG_STREET_REQUIRED)
    @Valid
    @Embedded
    private Street street;

    @NotNull(message = ERR_MSG_STREET_NUMBER_REQUIRED)
    @Valid
    @Embedded
    private StreetNumber streetNumber;

    @Nullable
    @Valid
    @Embedded
    private FloorDoor floorDoor;
}