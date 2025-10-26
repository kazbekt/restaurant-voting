package ru.javaops.restaurantvoting.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import ru.javaops.restaurantvoting.common.model.NamedEntity;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {
}
