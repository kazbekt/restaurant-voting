package ru.javaops.restaurantvoting.common.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

@Converter(autoApply = true)
public class MoneyConverter implements AttributeConverter<MonetaryAmount, BigDecimal> {

    @Override
    public BigDecimal convertToDatabaseColumn(MonetaryAmount money) {
        return money != null ? money.getNumber().numberValue(BigDecimal.class) : null;
    }

    @Override
    public MonetaryAmount convertToEntityAttribute(BigDecimal amount) {
        return amount != null ?
                Monetary.getDefaultAmountFactory()
                        .setNumber(amount)
                        .setCurrency("RUB")
                        .create() : null;
    }
}
