package bar.imagine.demo.repository;

import bar.imagine.demo.data.address.ZipCityMapping;
import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.ZipCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@ActiveProfiles("test")
class ZipCityMappingRepositoryTest {

    @Autowired
    private ZipCityMappingRepository zipCityMappingRepository;

    @Test
    void save_persistsMapping_andFindByZipCodeValueReturnsIt() {
        ZipCityMapping mapping = ZipCityMapping.builder()
            .zipCode(new ZipCode("1011"))
            .city(new City("Budapest"))
            .build();

        zipCityMappingRepository.save(mapping);

        assertTrue(zipCityMappingRepository.findByZipCode_Value("1011").isPresent());
        assertEquals("Budapest", zipCityMappingRepository.findByZipCode_Value("1011").get().getCity().getValue());
    }

    @Test
    void save_throwsDataIntegrityViolationException_onDuplicateZipCode() {
        zipCityMappingRepository.saveAndFlush(ZipCityMapping.builder()
            .zipCode(new ZipCode("1011"))
            .city(new City("Budapest"))
            .build());

        ZipCityMapping duplicate = ZipCityMapping.builder()
            .zipCode(new ZipCode("1011"))
            .city(new City("Szeged"))
            .build();

        assertThrows(DataIntegrityViolationException.class, () -> zipCityMappingRepository.saveAndFlush(duplicate));
    }
}
