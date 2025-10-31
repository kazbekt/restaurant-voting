package ru.javaops.restaurantvoting.menu.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.common.BaseRepository;
import ru.javaops.restaurantvoting.menu.model.Meal;

import java.util.List;

@Transactional(readOnly = true)
public interface MealRepository extends BaseRepository<Meal> {
    @Query("SELECT m FROM Meal m WHERE m.id IN :ids")
    List<Meal> findAllByIds(List<Integer> ids);

    @Query("SELECT COUNT(menu) > 0 FROM Menu menu JOIN menu.meals m WHERE m.id = :mealId AND menu.date = CURRENT_DATE")
    boolean isMealUsedInMenus(int mealId);

    @Modifying
    @Query("DELETE FROM Menu menu WHERE menu IN (SELECT m FROM Menu m JOIN m.meals meal WHERE meal.id = :mealId) AND menu.date > CURRENT_DATE")
    void deleteMealFromFutureMenus(int mealId);
}
