package ru.javaops.restaurantvoting.restaurant.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.restaurantvoting.common.model.NamedEntity;
import ru.javaops.restaurantvoting.menu.model.Menu;

import java.util.List;

@Entity
@Table(name = "restaurant")
@NamedEntityGraph(name = "Restaurant.withMenus", attributeNodes = @NamedAttributeNode("menus"))
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class Restaurant extends NamedEntity {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OrderBy("date DESC")
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Menu> menus;
}