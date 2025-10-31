package ru.javaops.restaurantvoting.restaurant.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.common.util.JsonUtil;
import ru.javaops.restaurantvoting.restaurant.model.Restaurant;
import ru.javaops.restaurantvoting.restaurant.repository.RestaurantRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurantvoting.restaurant.RestaurantTestData.*;
import static ru.javaops.restaurantvoting.restaurant.RestaurantsUtil.getRestaurantTos;
import static ru.javaops.restaurantvoting.restaurant.web.RestaurantAdminController.REST_URL;
import static ru.javaops.restaurantvoting.user.UserTestData.ADMIN_MAIL;

class RestaurantAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private RestaurantRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + WISHBAR_ID)).andExpect(status().isOk()).andDo(print()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(RESTAURANT_MATCHER.contentJson(WISHBAR));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(RestaurantAdminController.REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(newRestaurant)));

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(repository.getExisted(newId), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + WISHBAR_ID).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated))).andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(repository.getExisted(WISHBAR_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + WISHBAR_ID)).andExpect(status().isNoContent());
        assertFalse(repository.findById(WISHBAR_ID).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(RestaurantAdminController.REST_URL)).andExpect(status().isOk()).andDo(print()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)).andExpect(Restaurant_TO_MATCHER.contentJson(getRestaurantTos(ALL_RESTAURANTS)));
    }
}