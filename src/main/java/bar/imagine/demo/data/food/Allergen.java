package bar.imagine.demo.data.food;

import static bar.imagine.demo.util.foodUtils.AllergenUtils.ERR_MSG_ALLERGEN_NAME_REQUIRED;
import static bar.imagine.demo.util.foodUtils.AllergenUtils.ERR_MSG_ICON_NAME_REQUIRED;

import bar.imagine.demo.data.food.allergen.AllergenName;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "ALLERGENS")
@Builder
@AttributeOverride(name = "name.value", column = @Column(name = "NAME", nullable = false, unique = true))
public class Allergen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_ALLERGEN_NAME_REQUIRED)
    private AllergenName name;

    @Column(name = "ICON_NAME", nullable = false)
    @NotBlank(message = ERR_MSG_ICON_NAME_REQUIRED)
    private String iconName;
}