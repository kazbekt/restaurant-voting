package ru.javaops.restaurantvoting.menu;

import lombok.experimental.UtilityClass;
import ru.javaops.restaurantvoting.menu.model.Meal;
import ru.javaops.restaurantvoting.menu.model.Menu;
import ru.javaops.restaurantvoting.menu.to.MealTo;
import ru.javaops.restaurantvoting.menu.to.MenuTo;
import ru.javaops.restaurantvoting.restaurant.to.RestaurantTo;

import java.util.Collection;
import java.util.List;

@UtilityClass
public class MenusUtil {

    public static MenuTo createTo(Menu menu) {
        return new MenuTo(
                menu.getId(),
                menu.getDate(),
                new RestaurantTo(menu.getRestaurant().getId(), menu.getRestaurant().getName()),
                menu.getMeals().stream()
                        .map(meal -> new MealTo(meal.getId(), meal.getName(), meal.getPrice()))
                        .toList()
        );
    }

    public static List<MenuTo> getTos(Collection<Menu> menus) {
        return menus.stream()
                .map(MenusUtil::createTo)
                .toList();
    }

    public static MealTo createTo(Meal meal) {
        return new MealTo(meal.getId(), meal.getName(), meal.getPrice());
    }

    public static List<MealTo> getMealTos(Collection<Meal> meals) {
        return meals.stream()
                .map(MenusUtil::createTo)
                .toList();
    }

    public static List<MenuTo> getMenuTos(Collection<Menu> menus) {
        return menus.stream()
                .map(MenusUtil::createTo)
                .toList();
    }
}