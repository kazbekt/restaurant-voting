package ru.javaops.restaurantvoting.menu.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.restaurantvoting.common.to.NamedTo;

import javax.money.MonetaryAmount;

@Value
@EqualsAndHashCode(callSuper = true)
public class MealTo extends NamedTo {

    MonetaryAmount price;

    public MealTo(Integer id, String name, MonetaryAmount price) {
        super(id, name);
        this.price = price;
    }
}