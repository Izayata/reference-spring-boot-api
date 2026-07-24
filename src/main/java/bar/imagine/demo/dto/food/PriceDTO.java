package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_INVALID_AMOUNT;
import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PriceUtils.PRICE_VALUE_MIN_AMOUNT;

import bar.imagine.demo.data.food.price.CurrencyEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class PriceDTO {
    @NotNull(message = ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED)
    @DecimalMin(value = PRICE_VALUE_MIN_AMOUNT, message = ERR_MSG_PRICE_VALUE_INVALID_AMOUNT)
    private BigDecimal amount;

    @NotNull(message = ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED)
    private CurrencyEnum currency;

    @JsonCreator
    public PriceDTO(@JsonProperty("amount") BigDecimal amount, @JsonProperty("currency") CurrencyEnum currency) {
        this.amount = amount;
        this.currency = currency;
    }
}
