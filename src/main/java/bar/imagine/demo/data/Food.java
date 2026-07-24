package bar.imagine.demo.data;

import static bar.imagine.demo.util.FoodUtils.ERR_MSG_ALLERGEN_LIST_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_CATEGORY_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_DESCRIPTION_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_FOOD_NAME_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_IMAGE_URL_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_INGREDIENT_LIST_REQUIRED;
import static bar.imagine.demo.util.FoodUtils.ERR_MSG_PRICE_REQUIRED;
import static bar.imagine.demo.util.foodUtils.DescriptionUtils.DESCRIPTION_VALUE_MAX_LENGTH;
import static bar.imagine.demo.util.foodUtils.ImageURLUtils.IMAGE_URL_VALUE_MAX_LENGTH;
import static bar.imagine.demo.util.foodUtils.PlaceToBuyUtils.ERR_MSG_PLACE_TO_BUY_REQUIRED;

import bar.imagine.demo.data.food.Allergen;
import bar.imagine.demo.data.food.CategoryEnum;
import bar.imagine.demo.data.food.Description;
import bar.imagine.demo.data.food.FoodName;
import bar.imagine.demo.data.food.ImageURL;
import bar.imagine.demo.data.food.Ingredient;
import bar.imagine.demo.data.food.PlaceToBuyEnum;
import bar.imagine.demo.data.food.Price;
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

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FOODS")
@Builder
@AttributeOverrides({
    @AttributeOverride(name = "foodName.value", column = @Column(name = "FOOD_NAME", nullable = false, unique = true)),
    @AttributeOverride(name = "description.value", column = @Column(name = "DESCRIPTION", nullable = false, length = DESCRIPTION_VALUE_MAX_LENGTH)),
    @AttributeOverride(name = "imageURL.value", column = @Column(name = "IMAGE_URL", nullable = false, length = IMAGE_URL_VALUE_MAX_LENGTH))
})
public class Food {
    @Column(name = "ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_FOOD_NAME_REQUIRED)
    private FoodName foodName;

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_PRICE_REQUIRED)
    @AttributeOverrides({
        @AttributeOverride(name = "amount",   column = @Column(name = "PRICE_AMOUNT")),
        @AttributeOverride(name = "currency", column = @Column(name = "PRICE_CURRENCY"))
    })
    private Price price;

    @Column(name = "PLACE_TO_BUY")
    @Enumerated(EnumType.STRING)
    @NotNull(message = ERR_MSG_PLACE_TO_BUY_REQUIRED)
    private PlaceToBuyEnum placeToBuy;

    @Column(name = "CATEGORY")
    @Enumerated(EnumType.STRING)
    @NotNull(message = ERR_MSG_CATEGORY_REQUIRED)
    private CategoryEnum category;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "FOOD_ALLERGENS",
        joinColumns = @JoinColumn(name = "FOOD_ID"),
        inverseJoinColumns = @JoinColumn(name = "ALLERGEN_ID")
    )
    @Valid
    @NotNull(message = ERR_MSG_ALLERGEN_LIST_REQUIRED)
    @Builder.Default
    private Set<Allergen> allergens = new HashSet<>();

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_DESCRIPTION_REQUIRED)
    private Description description;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
        name = "FOOD_INGREDIENTS",
        joinColumns = @JoinColumn(name = "FOOD_ID"),
        inverseJoinColumns = @JoinColumn(name = "INGREDIENT_ID")
    )
    @Valid
    @NotNull(message = ERR_MSG_INGREDIENT_LIST_REQUIRED)
    @Builder.Default
    private Set<Ingredient> ingredients = new HashSet<>();

    @Embedded
    @Valid
    @NotNull(message = ERR_MSG_IMAGE_URL_REQUIRED)
    private ImageURL imageURL;
}
