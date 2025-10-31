package ru.javaops.restaurantvoting.vote.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.restaurantvoting.common.to.BaseTo;

import java.time.LocalDate;
import java.time.LocalTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    Integer restaurantId;
    LocalDate voteDate;
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime voteTime;

    public VoteTo(Integer id, Integer restaurantId, LocalDate voteDate, LocalTime voteTime) {
        super(id);
        this.restaurantId = restaurantId;
        this.voteDate = voteDate;
        this.voteTime = voteTime;
    }
}