package ru.javaops.restaurantvoting.menu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.javaops.restaurantvoting.common.model.NamedEntity;
import ru.javaops.restaurantvoting.common.util.MoneyConverter;

import javax.money.MonetaryAmount;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal extends NamedEntity {

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @Convert(converter = MoneyConverter.class)
    @NotNull
    @Positive(message = "Price must be positive")
    private MonetaryAmount price;

    public Meal(Integer id, String name, MonetaryAmount price) {
        super(id, name);
        this.price = price;
    }
}
