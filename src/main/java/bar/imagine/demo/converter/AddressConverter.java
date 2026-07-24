package bar.imagine.demo.converter;

import bar.imagine.demo.data.customer.Address;
import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.FloorDoor;
import bar.imagine.demo.data.customer.address.Street;
import bar.imagine.demo.data.customer.address.StreetNumber;
import bar.imagine.demo.data.customer.address.ZipCode;
import bar.imagine.demo.dto.customer.AddressDTO;
import bar.imagine.demo.dto.customer.address.CityDTO;
import bar.imagine.demo.dto.customer.address.FloorDoorDTO;
import bar.imagine.demo.dto.customer.address.StreetDTO;
import bar.imagine.demo.dto.customer.address.StreetNumberDTO;
import bar.imagine.demo.dto.customer.address.ZipCodeDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressConverter {
    public Address convertAddressDtoToAddress(AddressDTO addressDto) {
        if (addressDto == null) return null;
        return Address.builder()
            .zipCode(new ZipCode(addressDto.getZipCode().getValue()))
            .city(new City(addressDto.getCity().getValue()))
            .street(new Street(addressDto.getStreet().getValue()))
            .streetNumber(new StreetNumber(addressDto.getStreetNumber().getValue()))
            .floorDoor(addressDto.getFloorDoor() != null ?
                new FloorDoor(addressDto.getFloorDoor().getValue()) : null)
            .build();
    }

    public AddressDTO convertAddressToAddressDto(Address address) {
        if (address == null) return null;
        return AddressDTO.builder()
            .zipCode(new ZipCodeDTO(address.getZipCode().getValue()))
            .city(new CityDTO(address.getCity().getValue()))
            .street(new StreetDTO(address.getStreet().getValue()))
            .streetNumber(new StreetNumberDTO(address.getStreetNumber().getValue()))
            .floorDoor(address.getFloorDoor() != null ?
                new FloorDoorDTO(address.getFloorDoor().getValue()) : null)
            .build();
    }
}
