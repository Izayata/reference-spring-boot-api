package bar.imagine.demo.data.food;

import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_INVALID_AMOUNT;
import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PriceUtils.PRICE_VALUE_MIN_AMOUNT;

import java.math.BigDecimal;

import bar.imagine.demo.data.food.price.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@Builder
@Embeddable
public class Price {
    @Column(name = "AMOUNT")
    @DecimalMin(value = PRICE_VALUE_MIN_AMOUNT, inclusive = true, message = ERR_MSG_PRICE_VALUE_INVALID_AMOUNT)
    @Digits(integer = 10, fraction = 2, message = ERR_MSG_PRICE_VALUE_INVALID_AMOUNT)
    @NotNull(message = ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED)
    private final BigDecimal amount;

    @Column(name = "CURRENCY")
    @Enumerated(EnumType.STRING)
    @NotNull(message = ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED)
    private final CurrencyEnum currency;

    @JsonCreator
    public Price(@JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") CurrencyEnum currency) {
        this.amount = amount;
        this.currency = currency;
    }

    // Required by JPA
    @SuppressWarnings("unused")
    protected Price() {
        this.amount = null;
        this.currency = null;
    }
}
