package bar.imagine.demo.dto.address;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class ZipCityLookupDTO {
    String zipCode;
    String city;

    @JsonCreator
    public ZipCityLookupDTO(@JsonProperty("zipCode") String zipCode, @JsonProperty("city") String city) {
        this.zipCode = zipCode;
        this.city = city;
    }
}
