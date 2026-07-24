package bar.imagine.demo.data.customer;

import static bar.imagine.demo.data.customer.address.CityTest.VALID_CITY_ASCII_ONLY;
import static bar.imagine.demo.data.customer.address.FloorDoorTest.VALID_FLOOR_DOOR_NUMBER_SLASH_NUMBER;
import static bar.imagine.demo.data.customer.address.StreetNumberTest.VALID_STREET_NUMBER;
import static bar.imagine.demo.data.customer.address.StreetTest.VALID_STREET;
import static bar.imagine.demo.data.customer.address.ZipCodeTest.VALID_ZIP;
import static bar.imagine.demo.util.customerUtils.addressUtils.CityUtils.ERR_MSG_CITY_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetNumberUtils.ERR_MSG_STREET_NUMBER_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.StreetUtils.ERR_MSG_STREET_REQUIRED;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_REQUIRED;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;


public class AddressTest {

    private static ValidatorFactory factory;
    private static Validator validator;
    private static final Address ERR_ADDRESS_ZIP_NULL = Address.builder()
        .zipCode(null)
        .city(VALID_CITY_ASCII_ONLY)
        .street(VALID_STREET)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address ERR_ADDRESS_ZIP_MISSING = Address.builder()
        .city(VALID_CITY_ASCII_ONLY)
        .street(VALID_STREET)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address ERR_ADDRESS_CITY_NULL = Address.builder()
        .zipCode(VALID_ZIP)
        .city(null)
        .street(VALID_STREET)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address ERR_ADDRESS_CITY_MISSING = Address.builder()
        .zipCode(VALID_ZIP)
        .street(VALID_STREET)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address ERR_ADDRESS_STREET_NULL = Address.builder()
        .zipCode(VALID_ZIP)
        .city(VALID_CITY_ASCII_ONLY)
        .street(null)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address ERR_ADDRESS_STREET_MISSING = Address.builder()
        .zipCode(VALID_ZIP)
        .city(VALID_CITY_ASCII_ONLY)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address ERR_ADDRESS_STREET_NUMBER_NULL = Address.builder()
        .zipCode(VALID_ZIP)
        .city(VALID_CITY_ASCII_ONLY)
        .street(VALID_STREET)
        .streetNumber(null)
        .build();
    private static final Address ERR_ADDRESS_STREET_NUMBER_MISSING = Address.builder()
        .zipCode(VALID_ZIP)
        .city(VALID_CITY_ASCII_ONLY)
        .street(VALID_STREET)
        .build();
    public static final Address VALID_ADDRESS_REQUIRED_FIELDS_ONLY = Address.builder()
        .zipCode(VALID_ZIP)
        .city(VALID_CITY_ASCII_ONLY)
        .street(VALID_STREET)
        .streetNumber(VALID_STREET_NUMBER)
        .build();
    private static final Address VALID_ADDRESS_ALL_FIELDS = Address.builder()
        .zipCode(VALID_ZIP)
        .city(VALID_CITY_ASCII_ONLY)
        .street(VALID_STREET)
        .streetNumber(VALID_STREET_NUMBER)
        .floorDoor(VALID_FLOOR_DOOR_NUMBER_SLASH_NUMBER)
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
        void testAddressValidRequiredFieldsOnly() {
            Set<ConstraintViolation<Address>> violations = validator.validate(VALID_ADDRESS_REQUIRED_FIELDS_ONLY);
            assertTrue(violations.isEmpty());
        }

        @Test
        void testAddressValidAllFields() {
            Set<ConstraintViolation<Address>> violations = validator.validate(VALID_ADDRESS_ALL_FIELDS);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testAddressInvalidNull() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_ZIP_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_REQUIRED)));
        }

        @Test
        void testAddressInvalidZipCodeNull() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_ZIP_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_REQUIRED)));
        }

        @Test
        void testAddressInvalidZipCodeMissing() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_ZIP_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_ZIP_CODE_REQUIRED)));
        }

        @Test
        void testAddressInvalidCityNull() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_CITY_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_REQUIRED)));
        }

        @Test
        void testAddressInvalidCityMissing() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_CITY_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_CITY_REQUIRED)));
        }

        @Test
        void testAddressInvalidStreetNull() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_STREET_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_REQUIRED)));
        }

        @Test
        void testAddressInvalidStreetMissing() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_STREET_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_REQUIRED)));
        }

        @Test
        void testAddressInvalidStreetNumberNull() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_STREET_NUMBER_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_REQUIRED)));
        }

        @Test
        void testAddressInvalidStreetNumberMissing() {
            Set<ConstraintViolation<Address>> violations = validator.validate(ERR_ADDRESS_STREET_NUMBER_MISSING);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_STREET_NUMBER_REQUIRED)));
        }
    }
}
