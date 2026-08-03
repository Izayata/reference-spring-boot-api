package bar.imagine.demo.controller;

import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ERR_MSG_ZIP_CODE_INVALID_FORMAT;
import static bar.imagine.demo.util.customerUtils.addressUtils.ZipCodeUtils.ZIP_CODE_PATTERN;

import bar.imagine.demo.dto.address.ZipCityLookupDTO;
import bar.imagine.demo.service.ZipCityMappingService;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/v1/zip-codes")
@RequiredArgsConstructor
public class ZipCityMappingController {
    private final ZipCityMappingService zipCityMappingService;

    @GetMapping("/{zipCode}")
    public ZipCityLookupDTO getCityByZipCode(
        @PathVariable @Pattern(regexp = ZIP_CODE_PATTERN, message = ERR_MSG_ZIP_CODE_INVALID_FORMAT) String zipCode
    ) {
        return zipCityMappingService.getCityByZipCode(zipCode);
    }
}
