package bar.imagine.demo.data.address;

import bar.imagine.demo.data.customer.address.City;
import bar.imagine.demo.data.customer.address.ZipCode;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ZIP_CITY_MAPPINGS")
@Builder
@AttributeOverrides({
    @AttributeOverride(name = "zipCode.value", column = @Column(name = "ZIP_CODE", nullable = false, unique = true)),
    @AttributeOverride(name = "city.value", column = @Column(name = "CITY", nullable = false))
})
public class ZipCityMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    @Valid
    @NotNull
    private ZipCode zipCode;

    @Embedded
    @Valid
    @NotNull
    private City city;
}
