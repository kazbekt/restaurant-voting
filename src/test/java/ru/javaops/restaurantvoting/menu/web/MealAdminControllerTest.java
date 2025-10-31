package ru.javaops.restaurantvoting.menu.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.common.util.JsonUtil;
import ru.javaops.restaurantvoting.menu.model.Meal;
import ru.javaops.restaurantvoting.menu.repository.MealRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurantvoting.menu.MealTestData.*;
import static ru.javaops.restaurantvoting.menu.MenusUtil.getMealTos;
import static ru.javaops.restaurantvoting.menu.web.MealAdminController.IS_USED_IN_MENUS;
import static ru.javaops.restaurantvoting.menu.web.MealAdminController.REST_URL;
import static ru.javaops.restaurantvoting.user.UserTestData.ADMIN_MAIL;

class MealAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MealRepository mealRepository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + RICE_MEAL_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(RICE_MEAL));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Meal newMeal = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)));

        Meal created = MEAL_MATCHER.readFromJson(action);
        int newId = created.id();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(mealRepository.getExisted(newId), newMeal);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Meal updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + RICE_MEAL_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealRepository.getExisted(RICE_MEAL_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + OLD_MEAL_ID))
                .andExpect(status().isNoContent());
        assertFalse(mealRepository.findById(OLD_MEAL_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteWhileInTodayMenu() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + RICE_MEAL_ID))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(IS_USED_IN_MENUS)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_TO_MATCHER.contentJson(getMealTos(ALL_MEALS)));
    }
}