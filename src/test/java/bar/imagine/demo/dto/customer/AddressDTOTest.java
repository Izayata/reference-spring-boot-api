package bar.imagine.demo.dto.customer;

import static bar.imagine.demo.dto.customer.address.CityDTOTest.VALID_CITY_DTO_ASCII_ONLY;
import static bar.imagine.demo.dto.customer.address.FloorDoorDTOTest.VALID_FLOOR_DOOR_DTO_NUMBER_SLASH_NUMBER;
import static bar.imagine.demo.dto.customer.address.StreetNumberDTOTest.VALID_STREET_NUMBER_DTO;
import static bar.imagine.demo.dto.customer.address.StreetDTOTest.VALID_STREET_DTO;
import static bar.imagine.demo.dto.customer.address.ZipCodeDTOTest.VALID_ZIP_DTO;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;


public class AddressDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;
    private static final AddressDTO ERR_ADDRESS_DTO_ZIP_DTO_NULL = AddressDTO.builder()
        .zipCode(null)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(VALID_STREET_DTO)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_ZIP_DTO_MISSING = AddressDTO.builder()
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(VALID_STREET_DTO)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_CITY_DTO_NULL = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(null)
        .street(VALID_STREET_DTO)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_CITY_DTO_MISSING = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .street(VALID_STREET_DTO)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_STREET_DTO_NULL = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(null)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_STREET_DTO_MISSING = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_STREET_NUMBER_DTO_NULL = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(VALID_STREET_DTO)
        .streetNumber(null)
        .build();
    private static final AddressDTO ERR_ADDRESS_DTO_STREET_NUMBER_DTO_MISSING = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(VALID_STREET_DTO)
        .build();
    public static final AddressDTO VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(VALID_STREET_DTO)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .build();
    private static final AddressDTO VALID_ADDRESS_DTO_ALL_FIELDS = AddressDTO.builder()
        .zipCode(VALID_ZIP_DTO)
        .city(VALID_CITY_DTO_ASCII_ONLY)
        .street(VALID_STREET_DTO)
        .streetNumber(VALID_STREET_NUMBER_DTO)
        .floorDoor(VALID_FLOOR_DOOR_DTO_NUMBER_SLASH_NUMBER)
        .build();

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }


    @Nested
    @DisplayName("Valid")
    class Valid {

        @Test
        void testAddressDtoValidRequiredFieldsOnly() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(VALID_ADDRESS_DTO_REQUIRED_FIELDS_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testAddressDtoValidAllFields() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(VALID_ADDRESS_DTO_ALL_FIELDS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testAddressDtoInvalidNull() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_ZIP_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidZipCodeNull() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_ZIP_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidZipCodeMissing() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_ZIP_DTO_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidCityNull() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_CITY_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidCityMissing() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_CITY_DTO_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidStreetNull() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_STREET_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidStreetMissing() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_STREET_DTO_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidStreetNumberNull() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_STREET_NUMBER_DTO_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_REQUIRED)));
        }

        @Test
        void testAddressDtoInvalidStreetNumberMissing() {
            Set<ConstraintViolation<AddressDTO>> violations = validator.validate(ERR_ADDRESS_DTO_STREET_NUMBER_DTO_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_REQUIRED)));
        }
    }
}
