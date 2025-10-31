package ru.javaops.restaurantvoting.restaurant.web;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javaops.restaurantvoting.restaurant.RestaurantsUtil;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;
import ru.javaops.restaurantvoting.restaurant.repository.RestaurantRepository;
import ru.javaops.restaurantvoting.restaurant.to.RestaurantTo;

import java.util.List;

public class AbstractRestaurantController {

    @Autowired
    protected RestaurantRepository repository;

    public RestaurantTo get(int id) {
        Restaurant restaurant = repository.getExisted(id);
        return RestaurantsUtil.createTo(restaurant);
    }

    public List<RestaurantTo> getAllWithTodayMenu() {
       return  RestaurantsUtil.getRestaurantTos(repository.getAllWithTodayMenu());
    }

    public List<RestaurantTo> getAll() {
        return RestaurantsUtil.getRestaurantTos(repository.findAll());
    }
}