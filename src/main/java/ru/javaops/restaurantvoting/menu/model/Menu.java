package ru.javaops.restaurantvoting.menu.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.restaurantvoting.common.model.BaseEntity;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "menu", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"restaurant_id", "date"}, name = "uk_menu_restaurant_date_idx")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)

public class Menu extends BaseEntity {
    @Column(name = "date", nullable = false)
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "menu_meal",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id"),
            uniqueConstraints = @UniqueConstraint(
                    columnNames = {"menu_id", "meal_id"}
            ))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotEmpty(message = "Menu must contain at least one meal")
    private List<Meal> meals = new ArrayList<>();

    public Menu(Integer id, LocalDate date, Restaurant restaurant, List<Meal> meals) {
        this.id = id;
        this.date = date;
        this.restaurant = restaurant;
        this.meals = meals;
    }
}
