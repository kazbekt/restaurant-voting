package ru.javaops.restaurantvoting.common.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<Money, BigDecimal> {

    private static final CurrencyUnit DEFAULT_CURRENCY = CurrencyUnit.of("RUB");

    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        return money.getAmount();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal amount) {
        return Money.of(DEFAULT_CURRENCY, amount);
    }
}
