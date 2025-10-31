package ru.javaops.restaurantvoting.menu.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.common.BaseRepository;
import ru.javaops.restaurantvoting.menu.model.Menu;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface MenuRepository extends BaseRepository<Menu> {

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant JOIN FETCH m.meals WHERE m.id = :id")
    Optional<Menu> findByIdWithRestaurantAndMeals(int id);

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant JOIN FETCH m.meals")
    List<Menu> findAllWithRestaurantAndMeals();

    @Query("""
                SELECT m FROM Menu m 
                JOIN FETCH m.restaurant 
                JOIN FETCH m.meals 
                WHERE m.restaurant.id = :restaurantId 
                AND m.date = :date
            """)
    Optional<Menu> findMenuWithMealsByRestaurantIdAndDate(int restaurantId, LocalDate date);


    @Query("""
                SELECT m FROM Menu m 
                JOIN FETCH m.restaurant 
                JOIN FETCH m.meals 
                WHERE m.date = :date
            """)
    List<Menu> findAllMenusWithMealsByDate(LocalDate date);

    boolean existsByRestaurantIdAndDate(int restaurantId, LocalDate date);
}
