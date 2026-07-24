package bar.imagine.demo.dto.customer;

import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_REQUIRED;

import bar.imagine.demo.dto.customer.address.CityDTO;
import bar.imagine.demo.dto.customer.address.FloorDoorDTO;
import bar.imagine.demo.dto.customer.address.StreetDTO;
import bar.imagine.demo.dto.customer.address.StreetNumberDTO;
import bar.imagine.demo.dto.customer.address.ZipCodeDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AddressDTO {
    @Valid
    @NotNull(message = ERR_MSG_ZIP_CODE_REQUIRED)
    private ZipCodeDTO zipCode;

    @Valid
    @NotNull(message = ERR_MSG_CITY_REQUIRED)
    private CityDTO city;

    @Valid
    @NotNull(message = ERR_MSG_STREET_REQUIRED)
    private StreetDTO street;

    @Valid
    @NotNull(message = ERR_MSG_STREET_NUMBER_REQUIRED)
    private StreetNumberDTO streetNumber;

    @Valid
    @Nullable
    private FloorDoorDTO floorDoor;

    @JsonCreator
    public AddressDTO(
        @JsonProperty("zipCode") ZipCodeDTO zipCode,
        @JsonProperty("city") CityDTO city,
        @JsonProperty("street") StreetDTO street,
        @JsonProperty("streetNumber") StreetNumberDTO streetNumber,
        @JsonProperty("floorDoor") FloorDoorDTO floorDoor
    ) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
        this.streetNumber = streetNumber;
        this.floorDoor = floorDoor;
    }
}
