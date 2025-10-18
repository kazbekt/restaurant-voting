package ru.javaops.restaurantvoting.menu;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.joda.money.Money;
import ru.javaops.restaurantvoting.common.model.NamedEntity;
import ru.javaops.restaurantvoting.common.util.MoneyConverter;

@Entity
@Table(name = "meal")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

public class Meal extends NamedEntity {

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    @Convert(converter = MoneyConverter.class)
    @NotNull
    private Money price;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(min = 2, max = 120)
    private String description;
}

