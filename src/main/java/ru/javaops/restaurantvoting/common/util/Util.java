package ru.javaops.restaurantvoting.common.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalTime;

@UtilityClass
public class Util {
    public static final String UPDATE_AFTER_DEADLINE_MSG = "Not allowed to alter after deadline";

    private static final LocalTime DEADLINE = LocalTime.of(11, 0);

    public boolean isAfterDeadline(LocalTime currentTime) {
        return currentTime.isAfter(DEADLINE);
    }

    public boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }

    public boolean isPast(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }
}