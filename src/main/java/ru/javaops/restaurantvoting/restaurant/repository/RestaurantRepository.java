package ru.javaops.restaurantvoting.restaurant.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.common.BaseRepository;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph("Restaurant.withMenus")
    @NonNull
    @Cacheable(value = "restaurants")
    List<Restaurant> findAll();

    @NonNull
    @Cacheable(value = "restaurant", key = "#id")
    Optional<Restaurant> findById(@NonNull Integer id);

    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.date = CURRENT_DATE")
    @Cacheable(value = "restaurantsWithTodayMenu", key = "'today'")
    List<Restaurant> getAllWithTodayMenu();
}