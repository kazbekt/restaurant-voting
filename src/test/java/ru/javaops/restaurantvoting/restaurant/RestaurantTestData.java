package ru.javaops.restaurantvoting.restaurant;

import ru.javaops.restaurantvoting.MatcherFactory;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;
import ru.javaops.restaurantvoting.restaurant.to.RestaurantTo;

import java.util.List;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menus");

    public static MatcherFactory.Matcher<RestaurantTo> Restaurant_TO_MATCHER = MatcherFactory.usingEqualsComparator(RestaurantTo.class);


    public static final int WISHBAR_ID = 10;
    public static final int OGONYOK_ID = 11;
    public static final int SWISH_SWISH_ID = 12;
    public static final int WITH_NO_MENU_ID = 13;

    public static final String WISHBAR_NAME = "Wishbar";
    public static final String OGONYOK_NAME = "Огонёк";
    public static final String SWISH_SWISH_NAME = "Swish Swish";
    public static final String WITH_NO_MENU_NAME = "Ресторан без меню";

    public static final Restaurant WISHBAR = new Restaurant(WISHBAR_ID, WISHBAR_NAME);
    public static final Restaurant OGONYOK = new Restaurant(OGONYOK_ID, OGONYOK_NAME);
    public static final Restaurant SWISH_SWISH = new Restaurant(SWISH_SWISH_ID, SWISH_SWISH_NAME);
    public static final Restaurant WITH_NO_MENU = new Restaurant(WITH_NO_MENU_ID, WITH_NO_MENU_NAME);

    public static final List<Restaurant> ALL_RESTAURANTS = List.of(WISHBAR, OGONYOK, SWISH_SWISH, WITH_NO_MENU);

    public static Restaurant getNew() {
        return new Restaurant(null, "Новый ресторан");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(WISHBAR_ID, WISHBAR_NAME + "_UPDATED");
    }
}
