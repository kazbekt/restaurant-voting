package ru.javaops.restaurantvoting.restaurant.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurantvoting.restaurant.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class RestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/restaurants";

    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return super.get(id);
    }

    @GetMapping("/with-menus/today")
    public List<RestaurantTo> getAllWithTodayMenu() {
        return super.getAllWithTodayMenu();
    }

    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }
}