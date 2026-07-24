package bar.imagine.demo.dto.food;

import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED;
import static bar.imagine.demo.util.foodUtils.PriceUtils.ERR_MSG_PRICE_VALUE_INVALID_AMOUNT;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Set;

import bar.imagine.demo.data.food.price.CurrencyEnum;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;

class PriceDTOTest {
    private static ValidatorFactory factory;
    private static Validator validator;

    private static final PriceDTO VALID_PRICE_DTO = PriceDTO.builder()
        .amount(new BigDecimal("10.50"))
        .currency(CurrencyEnum.USD)
        .build();

    private static final PriceDTO ERR_PRICE_DTO_AMOUNT_NULL = PriceDTO.builder()
        .amount(null)
        .currency(CurrencyEnum.USD)
        .build();

    private static final PriceDTO ERR_PRICE_DTO_AMOUNT_ZERO = PriceDTO.builder()
        .amount(BigDecimal.ZERO)
        .currency(CurrencyEnum.USD)
        .build();

    private static final PriceDTO ERR_PRICE_DTO_AMOUNT_NEGATIVE = PriceDTO.builder()
        .amount(new BigDecimal("-5.00"))
        .currency(CurrencyEnum.USD)
        .build();

    private static final PriceDTO ERR_PRICE_DTO_CURRENCY_NULL = PriceDTO.builder()
        .amount(new BigDecimal("10.50"))
        .currency(null)
        .build();

    private static final PriceDTO ERR_PRICE_DTO_AMOUNT_AND_CURRENCY_NULL = PriceDTO.builder()
        .amount(null)
        .currency(null)
        .build();

    @BeforeAll
    static void setupValidator() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @AfterAll
    static void tearDown() {
        factory.close();
    }


    @Nested
    @DisplayName("Valid")
    class Valid {

        @Test
        void testPriceDtoValid() {
            Set<ConstraintViolation<PriceDTO>> violations = validator.validate(VALID_PRICE_DTO);
            assertTrue(violations.isEmpty());
        }

    }

    @Nested
    @DisplayName("Invalid")
    class Invalid {

        @Test
        void testPriceDtoInvalidAmountNull() {
            Set<ConstraintViolation<PriceDTO>> violations = validator.validate(ERR_PRICE_DTO_AMOUNT_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED)));
        }

        @Test
        void testPriceDtoInvalidAmountZero() {
            Set<ConstraintViolation<PriceDTO>> violations = validator.validate(ERR_PRICE_DTO_AMOUNT_ZERO);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_VALUE_INVALID_AMOUNT)));
        }

        @Test
        void testPriceDtoInvalidAmountNegative() {
            Set<ConstraintViolation<PriceDTO>> violations = validator.validate(ERR_PRICE_DTO_AMOUNT_NEGATIVE);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_VALUE_INVALID_AMOUNT)));
        }

        @Test
        void testPriceDtoInvalidCurrencyNull() {
            Set<ConstraintViolation<PriceDTO>> violations = validator.validate(ERR_PRICE_DTO_CURRENCY_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED)));
        }

        @Test
        void testPriceDtoInvalidAmountAndCurrencyNull() {
            Set<ConstraintViolation<PriceDTO>> violations = validator.validate(ERR_PRICE_DTO_AMOUNT_AND_CURRENCY_NULL);
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_VALUE_AMOUNT_REQUIRED)));
            assertTrue(violations.stream().anyMatch(v -> v.getMessage().equals(ERR_MSG_PRICE_VALUE_CURRENCY_REQUIRED)));
        }
    }
}
