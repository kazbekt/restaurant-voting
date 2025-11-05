package ru.javaops.restaurantvoting.menu.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javaops.restaurantvoting.menu.MenusUtil;
import ru.javaops.restaurantvoting.menu.model.Menu;
import ru.javaops.restaurantvoting.menu.repository.MenuRepository;
import ru.javaops.restaurantvoting.menu.service.MenuService;
import ru.javaops.restaurantvoting.menu.to.MenuTo;

import java.net.URI;
import java.util.List;

import static ru.javaops.restaurantvoting.common.validation.ValidationUtil.assureIdConsistent;
import static ru.javaops.restaurantvoting.common.validation.ValidationUtil.checkIsNew;


@RestController
@RequestMapping(value = MenuAdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MenuAdminController {

    static final String REST_URL = "/api/admin/menus";

    private final MenuRepository repository;
    private final MenuService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @CacheEvict(value =
            {"menus", "allMenus", "menusByDate", "menuByRestaurantAndDate", "restaurantsWithTodayMenu"}, allEntries = true)
    public ResponseEntity<Menu> createWithLocation(@Valid @RequestBody Menu menu) {
        log.info("create {}", menu);
        checkIsNew(menu);
        Menu created = service.create(menu);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value =
            {"menus", "allMenus", "menusByDate", "menuByRestaurantAndDate", "restaurantsWithTodayMenu"}, allEntries = true)
    public void update(@Valid @RequestBody Menu menu, @PathVariable int id) {
        log.info("update {} with id={}", menu, id);
        assureIdConsistent(menu, id);
        repository.getExisted(id);
        menu.setId(id);
        service.update(menu);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value =
            {"menus", "allMenus", "menusByDate", "menuByRestaurantAndDate", "restaurantsWithTodayMenu"}, allEntries = true)
    public void delete(@PathVariable int id) {
        log.info("delete menu with id={}", id);
        service.delete(id);
    }

    @GetMapping
    public List<MenuTo> getAll() {
        return MenusUtil.getMenuTos(repository.findAllWithRestaurantAndMeals());
    }
}