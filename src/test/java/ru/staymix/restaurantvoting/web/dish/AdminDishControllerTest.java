package ru.staymix.restaurantvoting.web.dish;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.service.DishService;
import ru.staymix.restaurantvoting.util.JsonUtil;
import ru.staymix.restaurantvoting.web.AbstractControllerTest;
import ru.staymix.restaurantvoting.web.restaurant.AdminRestaurantController;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.staymix.restaurantvoting.testdata.DishTestData.*;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.RESTAURANT_ID;
import static ru.staymix.restaurantvoting.testdata.UserTestData.ADMIN_MAIL;
import static ru.staymix.restaurantvoting.testdata.UserTestData.USER_MAIL;

class AdminDishControllerTest extends AbstractControllerTest {

    private static final String REST_DISH_URL_BASE = AdminRestaurantController.REST_URL + "/{restaurantId}/dishes";
    private static final String REST_DISH_URL_BASE_SLASH = AdminRestaurantController.REST_URL + "/{restaurantId}/dishes/{id}";

    @Autowired
    private DishService service;

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_DISH_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());

        Dish created = DISH_MATCHER.readFromJson(action);
        int id = created.id();
        newDish.setId(id);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(id, RESTAURANT_ID), newDish);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createInvalid() throws Exception {
        Dish invalid = getNew();
        invalid.setName(null);
        perform(MockMvcRequestBuilders.post(REST_DISH_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void createDuplicateToday() throws Exception {
        Dish invalid = getNew();
        invalid.setName(dish2.getName());
        perform(MockMvcRequestBuilders.post(REST_DISH_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID, RESTAURANT_ID));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());

    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE, RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DISH_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE, RESTAURANT_ID)
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DISH_MATCHER.contentJson(menu1));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getMenuByDateEmptyList() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE + "/by-date", RESTAURANT_ID)
                .param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DISH_MATCHER.contentJson(List.of()));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isNoContent());

        DISH_MATCHER.assertMatch(service.get(DISH_ID, RESTAURANT_ID), updated);
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateInvalid() throws Exception {
        Dish invalid = getUpdated();
        invalid.setName("");
        perform(MockMvcRequestBuilders.put(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        Dish invalid = getUpdated();
        invalid.setName("<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void updateDuplicateToday() throws Exception {
        Dish invalid = getUpdated();
        invalid.setName(dish2.getName());
        perform(MockMvcRequestBuilders.put(REST_DISH_URL_BASE_SLASH, RESTAURANT_ID, DISH_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(invalid)))
                .andDo(print())
                .andExpect(status().isNoContent());

        perform(MockMvcRequestBuilders.get(REST_DISH_URL_BASE, RESTAURANT_ID)
                .param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isConflict())
                .andDo(print());
    }
}