package ru.javaops.restaurantvoting.menu.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.javaops.restaurantvoting.menu.service.MenuService;
import ru.javaops.restaurantvoting.menu.to.MenuTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = MenuController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class MenuController {
    static final String REST_URL = "/api/menus";

    private final MenuService service;

    @GetMapping("/{id}")
    public MenuTo get(@PathVariable int id) {
        return service.getMenuById(id);
    }

    @GetMapping("/by-restaurant")
    public ResponseEntity<MenuTo> getRestaurantMenuByDate(
            @RequestParam int restaurantId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        MenuTo menu = service.getMenuByRestaurantAndDate(restaurantId, targetDate);

        return ResponseEntity.ok(menu);
    }

    @GetMapping
    public List<MenuTo> getAllMenusByDate(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        return service.getAllMenuByDate(targetDate);
    }
}