package bar.imagine.demo.data.food;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_INGREDIENT_REQUIRED;

import bar.imagine.demo.data.food.ingredient.IngredientName;
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
@Table(name = "INGREDIENTS")
@Builder
@AttributeOverride(name = "name.value", column = @Column(name = "NAME", nullable = false, unique = true))
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_INGREDIENT_REQUIRED)
    private IngredientName name;
}
