package ru.javaops.restaurantvoting.menu;

import org.javamoney.moneta.Money;
import ru.javaops.restaurantvoting.MatcherFactory;
import ru.javaops.restaurantvoting.menu.model.Meal;
import ru.javaops.restaurantvoting.menu.to.MealTo;

import javax.money.MonetaryAmount;
import java.util.List;

public class MealTestData {

    public static final MatcherFactory.Matcher<Meal> MEAL_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(Meal.class);

    public static MatcherFactory.Matcher<MealTo> MEAL_TO_MATCHER = MatcherFactory.usingEqualsComparator(MealTo.class);

    public static final int RICE_MEAL_ID = 20;
    public static final int OMELETTE_MEAL_ID = 21;
    public static final int KEBAB_MEAL_ID = 22;
    public static final int PLOW_MEAL_ID = 23;
    public static final int GREEK_SALAD_MEAL_ID = 24;
    public static final int CHICKEN_SOUP_MEAL_ID = 25;
    public static final int SALMON_STEAK_MEAL_ID = 26;
    public static final int TIRAMISU_MEAL_ID = 27;
    public static final int GREEN_TEA_MEAL_ID = 28;
    public static final int OLD_MEAL_ID = 29;

    public static final String RICE_MEAL_NAME = "Рис отварной";
    public static final String OMELETTE_MEAL_NAME = "Яичница";
    public static final String KEBAB_MEAL_NAME = "Шашлык по-пакистански";
    public static final String PLOW_MEAL_NAME = "Плов узбекский";
    public static final String GREEK_SALAD_MEAL_NAME = "Салат Греческий";
    public static final String CHICKEN_SOUP_MEAL_NAME = "Куриный суп";
    public static final String SALMON_STEAK_MEAL_NAME = "Стейк из лосося";
    public static final String TIRAMISU_MEAL_NAME = "Тирамису";
    public static final String GREEN_TEA_MEAL_NAME = "Чай зеленый";
    public static final String OLD_MEAL_NAME = "Старое блюдо";

    public static final double RICE_MEAL_PRICE = 120.00;
    public static final double OMELETTE_MEAL_PRICE = 150.00;
    public static final double KEBAB_MEAL_PRICE = 1300.00;
    public static final double PLOW_MEAL_PRICE = 450.00;
    public static final double GREEK_SALAD_MEAL_PRICE = 320.00;
    public static final double CHICKEN_SOUP_MEAL_PRICE = 280.00;
    public static final double SALMON_STEAK_MEAL_PRICE = 890.00;
    public static final double TIRAMISU_MEAL_PRICE = 350.00;
    public static final double GREEN_TEA_MEAL_PRICE = 150.00;
    public static final double OLD_MEAL_PRICE = 190.00;

    public static final Meal RICE_MEAL = new Meal(RICE_MEAL_ID, RICE_MEAL_NAME, createMoney(RICE_MEAL_PRICE));
    public static final Meal OMELETTE_MEAL = new Meal(OMELETTE_MEAL_ID, OMELETTE_MEAL_NAME, createMoney(OMELETTE_MEAL_PRICE));
    public static final Meal KEBAB_MEAL = new Meal(KEBAB_MEAL_ID, KEBAB_MEAL_NAME, createMoney(KEBAB_MEAL_PRICE));
    public static final Meal PLOW_MEAL = new Meal(PLOW_MEAL_ID, PLOW_MEAL_NAME, createMoney(PLOW_MEAL_PRICE));
    public static final Meal GREEK_SALAD_MEAL = new Meal(GREEK_SALAD_MEAL_ID, GREEK_SALAD_MEAL_NAME, createMoney(GREEK_SALAD_MEAL_PRICE));
    public static final Meal CHICKEN_SOUP_MEAL = new Meal(CHICKEN_SOUP_MEAL_ID, CHICKEN_SOUP_MEAL_NAME, createMoney(CHICKEN_SOUP_MEAL_PRICE));
    public static final Meal SALMON_STEAK_MEAL = new Meal(SALMON_STEAK_MEAL_ID, SALMON_STEAK_MEAL_NAME, createMoney(SALMON_STEAK_MEAL_PRICE));
    public static final Meal TIRAMISU_MEAL = new Meal(TIRAMISU_MEAL_ID, TIRAMISU_MEAL_NAME, createMoney(TIRAMISU_MEAL_PRICE));
    public static final Meal GREEN_TEA_MEAL = new Meal(GREEN_TEA_MEAL_ID, GREEN_TEA_MEAL_NAME, createMoney(GREEN_TEA_MEAL_PRICE));
    public static final Meal OLD_MEAL = new Meal(OLD_MEAL_ID, OLD_MEAL_NAME, createMoney(OLD_MEAL_PRICE));

    public static final List<Meal> ALL_MEALS = List.of(
            RICE_MEAL, OMELETTE_MEAL, KEBAB_MEAL, PLOW_MEAL,
            GREEK_SALAD_MEAL, CHICKEN_SOUP_MEAL, SALMON_STEAK_MEAL,
            TIRAMISU_MEAL, GREEN_TEA_MEAL, OLD_MEAL);

    public static Meal getNew() {
        return new Meal(null, "Чак-чак", createMoney(100.00));
    }

    public static Meal getUpdated() {
        return new Meal(RICE_MEAL_ID, "Подорожавший рис", createMoney(149.99));
    }

    private static MonetaryAmount createMoney(Number amount) {
        return Money.of(amount, "RUB");
    }
}
