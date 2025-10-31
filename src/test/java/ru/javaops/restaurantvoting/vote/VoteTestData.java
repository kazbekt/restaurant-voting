package ru.javaops.restaurantvoting.vote;

import ru.javaops.restaurantvoting.MatcherFactory;
import ru.javaops.restaurantvoting.vote.model.Vote;
import ru.javaops.restaurantvoting.vote.to.VoteTo;

import java.time.LocalTime;

import static ru.javaops.restaurantvoting.AbstractControllerTest.TODAY;
import static ru.javaops.restaurantvoting.restaurant.RestaurantTestData.*;
import static ru.javaops.restaurantvoting.user.UserTestData.admin;
import static ru.javaops.restaurantvoting.user.UserTestData.user;

public class VoteTestData {

    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(Vote.class
                    , "user.password", "user.registered", "restaurant.menus", "restaurant.$$_hibernate_interceptor");

    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory
            .usingIgnoringFieldsComparator(VoteTo.class, "restaurant.menus");

    public static final LocalTime USER_VOTE_TIME = LocalTime.of(10, 30);
    public static final LocalTime USER_VOTE_TIME_AFTER_DEADLINE = LocalTime.of(11, 1);
    public static final LocalTime USER_VOTE_TIME_BEFORE_DEADLINE = LocalTime.of(10, 55);
    public static final int USER_VOTE_ID = 40;

    public static final Vote USER_VOTE = new Vote(USER_VOTE_ID, user, WISHBAR, TODAY,  USER_VOTE_TIME);

    public static Vote getNew() {
        return new Vote(null, admin, WISHBAR, TODAY, LocalTime.now());
    }

    public static Vote getUpdated() {
        return new Vote(40, user, SWISH_SWISH, TODAY, null);
    }
}