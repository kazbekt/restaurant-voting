package ru.javaops.restaurantvoting.menu.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.common.error.DataConflictException;
import ru.javaops.restaurantvoting.menu.MenusUtil;
import ru.javaops.restaurantvoting.menu.model.Meal;
import ru.javaops.restaurantvoting.menu.repository.MealRepository;
import ru.javaops.restaurantvoting.menu.to.MealTo;

import java.net.URI;
import java.util.List;

import static ru.javaops.restaurantvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.restaurantvoting.common.validation.ValidationUtil.checkIsNew;
import static ru.javaops.restaurantvoting.menu.MenusUtil.createTo;

@RestController
@RequestMapping(value = MealAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MealAdminController {

    static final String REST_URL = "/api/admin/meals";
    public static final String IS_USED_IN_MENUS = "Cannot delete meal that is used in today menus";

    private final MealRepository repository;

    @GetMapping("/{id}")
    public MealTo get(@PathVariable int id) {
        return createTo(repository.getExisted(id));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@Valid @RequestBody Meal meal) {
        log.info("create {}", meal);
        checkIsNew(meal);
        Meal created = repository.save(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}").buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody Meal meal, @PathVariable int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        repository.getExisted(id);
        meal.setId(id);
        repository.save(meal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        log.info("delete meal {}", id);

        if (repository.isMealUsedInMenus(id)) {
            throw new DataConflictException(IS_USED_IN_MENUS);
        }
        repository.deleteMealFromFutureMenus(id);
        repository.deleteExisted(id);
    }

    @GetMapping
    public List<MealTo> getAll() {
        log.info("getAll meals");
        return MenusUtil.getMealTos(repository.findAll());
    }
}