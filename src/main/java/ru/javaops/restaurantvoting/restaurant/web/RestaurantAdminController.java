package ru.javaops.restaurantvoting.restaurant.web;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;
import ru.javaops.restaurantvoting.restaurant.to.RestaurantTo;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static ru.javaops.restaurantvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.restaurantvoting.common.validation.ValidationUtil.checkIsNew;

@RestController
@RequestMapping(value = RestaurantAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
@Tag(name = "Restaurant Admin Controller")
public class RestaurantAdminController extends AbstractRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    @GetMapping("/{id}")
    public RestaurantTo get(@Parameter(example = "12") @PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value = {"restaurants", "restaurantsWithTodayMenu"}, allEntries = true)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        log.info("create restaurant {}", restaurant);
        checkIsNew(restaurant);
        Restaurant created = repository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(value = {"restaurants", "restaurantsWithTodayMenu"}, allEntries = true)
    public void update(@Valid @RequestBody Restaurant restaurant, @Parameter(example = "12") @PathVariable int id) {
        log.info("update restaurant {}", restaurant);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    @CacheEvict(value = {"restaurants", "restaurantsWithTodayMenu"}, allEntries = true)
    public void delete(@Parameter(example = "13") @PathVariable int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
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