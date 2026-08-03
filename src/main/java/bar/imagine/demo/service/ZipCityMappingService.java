package bar.imagine.demo.service;

import bar.imagine.demo.data.address.ZipCityMapping;
import bar.imagine.demo.dto.address.ZipCityLookupDTO;
import bar.imagine.demo.repository.ZipCityMappingRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ZipCityMappingService {
    private final ZipCityMappingRepository zipCityMappingRepository;

    @Transactional(readOnly = true)
    public ZipCityLookupDTO getCityByZipCode(String zipCode) {
        ZipCityMapping mapping = zipCityMappingRepository.findByZipCode_Value(zipCode)
            .orElseThrow(() -> new NoSuchElementException("No city found for zip code: " + zipCode));
        return new ZipCityLookupDTO(mapping.getZipCode().getValue(), mapping.getCity().getValue());
    }
}
