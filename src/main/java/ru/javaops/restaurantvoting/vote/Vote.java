package ru.javaops.restaurantvoting.vote;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.javaops.restaurantvoting.common.model.BaseEntity;
import ru.javaops.restaurantvoting.restaurant.Restaurant;
import ru.javaops.restaurantvoting.user.model.User;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "vote", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "vote_date"}, name = "uk_vote_user_date")
})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Vote extends BaseEntity {

    @Column(name = "vote_date", nullable = false)
    @NotNull
    private LocalDate date;

    @Column(name = "vote_time", nullable = false)
    @NotNull
    private LocalTime time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    // @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @NotNull
    private Restaurant restaurant;

    public boolean isBeforeDeadline() {
        return this.time.isBefore(LocalTime.of(11, 0));
    }
}
