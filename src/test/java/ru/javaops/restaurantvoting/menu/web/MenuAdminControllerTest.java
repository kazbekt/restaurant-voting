package ru.javaops.restaurantvoting.menu.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.restaurantvoting.AbstractControllerTest;
import ru.javaops.restaurantvoting.common.util.JsonUtil;
import ru.javaops.restaurantvoting.menu.model.Menu;
import ru.javaops.restaurantvoting.menu.repository.MenuRepository;
import ru.javaops.restaurantvoting.menu.service.MenuService;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javaops.restaurantvoting.menu.MenuTestData.*;
import static ru.javaops.restaurantvoting.menu.MenusUtil.getMenuTos;
import static ru.javaops.restaurantvoting.menu.web.MenuAdminController.REST_URL;
import static ru.javaops.restaurantvoting.user.UserTestData.ADMIN_MAIL;

class MenuAdminControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private MenuRepository repository;

    @Autowired
    private MenuService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Menu newMenu = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMenu)));

        Menu created = MENU_MATCHER.readFromJson(action);
        int newId = created.id();
        newMenu.setId(newId);
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(repository.getExisted(newId), newMenu);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Menu updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL_SLASH + WISHBAR_FUTURE_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        MENU_MATCHER.assertMatch(repository.getExisted(WISHBAR_FUTURE_MENU_ID), updated);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    @Transactional(propagation = Propagation.NEVER)
    void delete() throws Exception {
        Menu newMenu = getNew();
        Menu created = service.create(newMenu);
        int createdId = created.id();

        assertEquals(created, repository.getExisted(createdId));
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + createdId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        assertFalse(repository.findById(createdId).isPresent());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteMenuWithVote() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_SLASH + WISHBAR_TODAY_MENU_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(MenuAdminController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_TO_MATCHER.contentJson(getMenuTos(ALL_MENUS)));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicateMenuForToday() throws Exception {
        Menu duplicatedMenu = getNew();
        duplicatedMenu.setDate(TODAY);

        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicatedMenu)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateTodayMenuAfterDeadline() throws Exception {
        Menu menu = getUpdated();
        menu.setDate(TODAY);

        //https://stackoverflow.com/questions/21105403/mocking-static-methods-with-mockito
        LocalTime fixedTime = LocalTime.of(11, 1);
        try (MockedStatic<LocalTime> mockLocalTime = Mockito.mockStatic(LocalTime.class, Mockito.CALLS_REAL_METHODS)) {
            mockLocalTime.when(LocalTime::now).thenReturn(fixedTime);
            perform(MockMvcRequestBuilders.post(REST_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.writeValue(menu)))
                    .andExpect(status().isUnprocessableEntity())
                    .andDo(print());
        }
    }
}
