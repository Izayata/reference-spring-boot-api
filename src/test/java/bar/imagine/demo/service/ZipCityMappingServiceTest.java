package bar.imagine.demo.service;

import bar.imagine.demo.data.address.ZipCityMapping;
import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.ZipCode;
import bar.imagine.demo.dto.address.ZipCityLookupDTO;
import bar.imagine.demo.repository.ZipCityMappingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZipCityMappingServiceTest {

    @Mock private ZipCityMappingRepository zipCityMappingRepository;

    @InjectMocks
    private ZipCityMappingService zipCityMappingService;

    @Test
    void getCityByZipCode_returnsDto_whenMappingExists() {
        ZipCityMapping mapping = ZipCityMapping.builder()
            .id(1L)
            .zipCode(new ZipCode("1011"))
            .city(new City("Budapest"))
            .build();
        when(zipCityMappingRepository.findByZipCode_Value("1011")).thenReturn(Optional.of(mapping));

        ZipCityLookupDTO result = zipCityMappingService.getCityByZipCode("1011");

        assertEquals("1011", result.getZipCode());
        assertEquals("Budapest", result.getCity());
    }

    @Test
    void getCityByZipCode_throwsNoSuchElementException_whenNoMappingExists() {
        when(zipCityMappingRepository.findByZipCode_Value("9999")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> zipCityMappingService.getCityByZipCode("9999"));
    }
}
