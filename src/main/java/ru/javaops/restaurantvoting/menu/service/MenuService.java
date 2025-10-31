package ru.javaops.restaurantvoting.menu.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.app.util.Util;
import ru.javaops.restaurantvoting.common.error.DataConflictException;
import ru.javaops.restaurantvoting.common.error.NotFoundException;
import ru.javaops.restaurantvoting.menu.MenusUtil;
import ru.javaops.restaurantvoting.menu.model.Meal;
import ru.javaops.restaurantvoting.menu.model.Menu;
import ru.javaops.restaurantvoting.menu.repository.MealRepository;
import ru.javaops.restaurantvoting.menu.repository.MenuRepository;
import ru.javaops.restaurantvoting.menu.to.MenuTo;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;
import ru.javaops.restaurantvoting.restaurant.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.vote.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javaops.restaurantvoting.app.util.Util.UPDATE_AFTER_DEADLINE_MSG;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class MenuService {

    private static final String MENU_FOR_PAST_DATE_MSG = "Cannot create/modify menu for past date";
    private static final String MENU_ALREADY_EXISTS_MSG = "Menu for today already exists for this restaurant. Try to update";
    private static final String MEALS_NOT_FOUND_MSG = "Some meals not found";
    public static final String VOTERS_NOTIFICATION_REQUIRED = "The menu has active votes. Voters notification required";
    public static final String NOT_FOUND_MENU_WITH_ID = "Not found menu with id ";
    public static final String MENU_NOT_FOUND_FOR_RESTAURANT_AND_DATE =
            "No menu found for restaurant id=%d for date %s";

    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    public MenuTo getMenuById(int id) {
        log.info("Get menu by id {}", id);
        Menu menu = menuRepository.findByIdWithRestaurantAndMeals(id)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_MENU_WITH_ID + id));
        return MenusUtil.createTo(menu);
    }

    public Menu create(Menu menu) {

        if (!isCreationAllowed(menu.getDate())) {
            throw new DataConflictException(MENU_FOR_PAST_DATE_MSG);
        }

        Restaurant restaurant = getAndCheckRestaurant(menu);

        if (Util.isToday(menu.getDate()) && menuRepository.existsByRestaurantIdAndDate(restaurant.getId(), menu.getDate())) {
            throw new DataConflictException(MENU_ALREADY_EXISTS_MSG);
        }

        menu.setRestaurant(restaurant);
        menu.setMeals(getManagedMeals(menu));

        return menuRepository.save(menu);
    }

    public void update(Menu menu) {
        Menu existing = menuRepository.getExisted(menu.getId());
        checkMenuModificationRestrictions(existing);
        updateMenu(existing, menu);
    }

    public void delete(int id) {
        Menu menu = menuRepository.getExisted(id);
        checkMenuModificationRestrictions(menu);
        menuRepository.deleteExisted(id);
    }

    public MenuTo getMenuByRestaurantAndDate(int restaurantId, LocalDate date) {
        Menu menu = menuRepository.findMenuWithMealsByRestaurantIdAndDate(restaurantId, date)
                .orElseThrow(() -> new NotFoundException(
                        String.format(MENU_NOT_FOUND_FOR_RESTAURANT_AND_DATE, restaurantId, date)));
        return MenusUtil.createTo(menu);
    }

    public List<MenuTo> getAllMenuByDate(LocalDate date) {
        log.info("Get menus from all restaurants for date {}", date);
        List<Menu> menus = menuRepository.findAllMenusWithMealsByDate(date);
        if (menus.isEmpty()) {
            throw new NotFoundException("No menus in restaurants for date " + date);
        } else {
            return MenusUtil.getTos(menus);
        }
    }

    private void updateMenu(Menu existing, Menu newMenu) {
        Restaurant restaurant = getAndCheckRestaurant(newMenu);
        existing.setDate(newMenu.getDate());
        existing.setRestaurant(restaurant);
        existing.getMeals().clear();
        existing.getMeals().addAll(getManagedMeals(newMenu));
        menuRepository.save(existing);
    }

    private List<Meal> getManagedMeals(Menu menu) {
        List<Integer> mealIds = extractMealIds(menu);
        List<Meal> managedMeals = mealRepository.findAllByIds(mealIds);
        validateAllMealsExist(managedMeals, mealIds);
        return managedMeals;
    }

    private List<Integer> extractMealIds(Menu menu) {
        return menu.getMeals().stream().map(Meal::getId).collect(Collectors.toList());
    }

    private void validateAllMealsExist(List<Meal> foundMeals, List<Integer> requestedIds) {
        if (foundMeals.size() != requestedIds.size()) {
            throw new NotFoundException(MEALS_NOT_FOUND_MSG);
        }
    }

    private Restaurant getAndCheckRestaurant(Menu menu) {
        return restaurantRepository.getExisted(menu.getRestaurant().getId());
    }

    private boolean isCreationAllowed(LocalDate menuDate) {
        LocalDate today = LocalDate.now();
        return !menuDate.isBefore(today);
    }

    private void checkMenuModificationRestrictions(Menu menu) {
        if (Util.isPast(menu.getDate())) {
            throw new DataConflictException(MENU_FOR_PAST_DATE_MSG);
        }
        if (Util.isToday(menu.getDate())) {
            checkTodayMenuModification(menu);
        }
    }

    private void checkTodayMenuModification(Menu menu) {
        if (Util.isAfterDeadline(LocalTime.now())) {
            throw new DataConflictException(UPDATE_AFTER_DEADLINE_MSG);
        }
        if (voteRepository.countByRestaurantAndDate(menu.getRestaurant().getId(), menu.getDate()) > 0) {
            throw new DataConflictException(VOTERS_NOTIFICATION_REQUIRED);
        }
    }
}