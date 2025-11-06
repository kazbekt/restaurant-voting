package ru.javaops.restaurantvoting.vote.web;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.common.util.JsonUtil;
import ru.javaops.restaurantvoting.vote.VotesUtil;
import ru.javaops.restaurantvoting.vote.model.Vote;
import ru.javaops.restaurantvoting.vote.repository.VoteRepository;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.javaops.restaurantvoting.common.util.Util.UPDATE_AFTER_DEADLINE_MSG;
import static ru.javaops.restaurantvoting.restaurant.RestaurantTestData.SWISH_SWISH_ID;
import static ru.javaops.restaurantvoting.restaurant.RestaurantTestData.WISHBAR_ID;
import static ru.javaops.restaurantvoting.user.UserTestData.ADMIN_MAIL;
import static ru.javaops.restaurantvoting.user.UserTestData.USER_MAIL;
import static ru.javaops.restaurantvoting.vote.VoteTestData.*;
import static ru.javaops.restaurantvoting.vote.service.VoteService.VOTED_TODAY;
import static ru.javaops.restaurantvoting.vote.web.VoteProfileController.REST_URL;


class VoteProfileControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteRepository repository;


    @Test
    @WithUserDetails(value = USER_MAIL)
    void getTodayVote() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + "today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(VotesUtil.createTo(USER_VOTE)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(WISHBAR_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)));

        Vote created = VOTE_MATCHER.readFromJson(action);
        int voteId = created.getId();
        newVote.setId(voteId);
        newVote.setTime(created.getTime().truncatedTo(ChronoUnit.SECONDS));
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(repository.getExisted(voteId), newVote);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void createSecondVoteToday() throws Exception {
        Vote newVote = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(SWISH_SWISH_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.detail")
                        .value(containsString(VOTED_TODAY)));

    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateBeforeDeadline() throws Exception {
        Vote updated = getUpdated();
        updated.setTime(USER_VOTE_TIME_BEFORE_DEADLINE);

        try (MockedStatic<LocalTime> mockLocalTime = Mockito.mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockLocalTime.when(LocalTime::now).thenReturn(USER_VOTE_TIME_BEFORE_DEADLINE);
            perform(MockMvcRequestBuilders.put(REST_URL)
                    .param("restaurantId", String.valueOf(SWISH_SWISH_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isNoContent());

            VOTE_MATCHER.assertMatch(repository.getExisted(USER_VOTE_ID), updated);
        }
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateAfterDeadline() throws Exception {
        Vote updated = getUpdated();
        updated.setTime(USER_VOTE_TIME_AFTER_DEADLINE);

        try (MockedStatic<LocalTime> mockLocalTime = Mockito.mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockLocalTime.when(LocalTime::now).thenReturn(USER_VOTE_TIME_AFTER_DEADLINE);
            perform(MockMvcRequestBuilders.put(REST_URL)
                    .param("restaurantId", String.valueOf(SWISH_SWISH_ID))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(updated)))
                    .andExpect(status().isConflict())
                    .andExpect(jsonPath("$.detail").value(containsString(UPDATE_AFTER_DEADLINE_MSG)));
        }
    }
}