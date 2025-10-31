package ru.javaops.restaurantvoting.vote.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Value;
import ru.javaops.restaurantvoting.common.to.BaseTo;

import java.time.LocalTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    Integer restaurantId;
    String restaurantName;
    @JsonFormat(pattern = "HH:mm:ss")
    LocalTime voteTime;

    public VoteTo(Integer id, Integer restaurantId, String restaurantName, LocalTime voteTime) {
        super(id);
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.voteTime = voteTime;
    }
}