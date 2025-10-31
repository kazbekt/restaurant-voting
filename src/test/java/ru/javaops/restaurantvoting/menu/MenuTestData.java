package ru.javaops.restaurantvoting.menu;

import ru.javaops.restaurantvoting.MatcherFactory;
import ru.javaops.restaurantvoting.menu.model.Meal;
import ru.javaops.restaurantvoting.menu.model.Menu;
import ru.javaops.restaurantvoting.menu.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

import static ru.javaops.restaurantvoting.AbstractControllerTest.TODAY;
import static ru.javaops.restaurantvoting.menu.MealTestData.*;
import static ru.javaops.restaurantvoting.restaurant.RestaurantTestData.*;

public class MenuTestData {

    public static final MatcherFactory.Matcher<Menu> MENU_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(Menu.class, "restaurant.menus", "meals.price", "restaurant.$$_hibernate_interceptor");

    public static MatcherFactory.Matcher<MenuTo> MENU_TO_MATCHER = MatcherFactory.usingEqualsComparator(MenuTo.class);


    public static final int WISHBAR_TODAY_MENU_ID = 30;
    public static final int OGONYOK_TODAY_MENU_ID = 31;
    public static final int SWISH_SWISH_TODAY_MENU_ID = 32;
    public static final int OGONYOK_PAST_MENU_ID = 33;
    public static final int WISHBAR_FUTURE_MENU_ID = 34;

    public static final List<Meal> WISHBAR_TODAY_MEALS = List.of(RICE_MEAL, OMELETTE_MEAL, KEBAB_MEAL, GREEN_TEA_MEAL);
    public static final List<Meal> OGONYOK_TODAY_MEALS = List.of(PLOW_MEAL, GREEK_SALAD_MEAL, CHICKEN_SOUP_MEAL, GREEN_TEA_MEAL);
    public static final List<Meal> SWISH_SWISH_TODAY_MEALS = List.of(SALMON_STEAK_MEAL, TIRAMISU_MEAL, GREEN_TEA_MEAL);
    public static final List<Meal> OGONYOK_PAST_MEALS = List.of(GREEN_TEA_MEAL);
    public static final List<Meal> WISHBAR_FUTURE_MEALS = List.of(RICE_MEAL);

    public static final LocalDate PAST_DATE = LocalDate.of(2025, 10, 1);
    public static final LocalDate FUTURE_DATE = LocalDate.of(2030, 1, 1);

    public static final Menu WISHBAR_TODAY_MENU = new Menu(WISHBAR_TODAY_MENU_ID, TODAY, WISHBAR, WISHBAR_TODAY_MEALS);
    public static final Menu OGONYOK_TODAY_MENU = new Menu(OGONYOK_TODAY_MENU_ID, TODAY, OGONYOK, OGONYOK_TODAY_MEALS);
    public static final Menu SWISH_SWISH_TODAY_MENU = new Menu(SWISH_SWISH_TODAY_MENU_ID, TODAY, SWISH_SWISH, SWISH_SWISH_TODAY_MEALS);
    public static final Menu OGONYOK_PAST_MENU = new Menu(OGONYOK_PAST_MENU_ID, PAST_DATE, OGONYOK, OGONYOK_PAST_MEALS);
    public static final Menu WISHBAR_FUTURE_MENU = new Menu(WISHBAR_FUTURE_MENU_ID, FUTURE_DATE, WISHBAR, WISHBAR_FUTURE_MEALS);

    public static final List<Menu> ALL_MENUS = List.of(
            WISHBAR_FUTURE_MENU,
            SWISH_SWISH_TODAY_MENU,
            WISHBAR_TODAY_MENU,
            OGONYOK_TODAY_MENU,
            OGONYOK_PAST_MENU

    );

    public static Menu getNew() {
        return new Menu(null, FUTURE_DATE.plusDays(1), WISHBAR, WISHBAR_FUTURE_MEALS);
    }

    public static Menu getUpdated() {
        return new Menu(WISHBAR_FUTURE_MENU_ID, FUTURE_DATE, WISHBAR, List.of(RICE_MEAL));
    }
}
