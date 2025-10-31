package ru.javaops.restaurantvoting.menu.to;

import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.restaurantvoting.common.to.BaseTo;
import ru.javaops.restaurantvoting.restaurant.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
public class MenuTo extends BaseTo {

    LocalDate date;
    RestaurantTo restaurant;
    List<MealTo> meals;

    public MenuTo(Integer id, LocalDate date, RestaurantTo restaurant, List<MealTo> meals) {
        super(id);
        this.date = date;
        this.restaurant = restaurant;
        this.meals = meals;
    }
}