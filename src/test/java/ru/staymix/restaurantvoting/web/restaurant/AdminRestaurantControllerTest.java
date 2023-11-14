package ru.staymix.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.service.RestaurantService;
import ru.staymix.restaurantvoting.to.RestaurantTo;
import ru.staymix.restaurantvoting.util.JsonUtil;
import ru.staymix.restaurantvoting.util.RestaurantsUtil;
import ru.staymix.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;
import static ru.staymix.restaurantvoting.testdata.UserTestData.ADMIN_MAIL;
import static ru.staymix.restaurantvoting.testdata.UserTestData.USER_MAIL;
import static ru.staymix.restaurantvoting.web.restaurant.AdminRestaurantController.REST_URL;

class AdminRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_RESTAURANT_URL_BASE = REST_URL + "/{restaurantId}";
    private static final String REST_MENU_BY_DATE_URL = "/menu/by-date";
    protected static final String REST_MENU_TODAY_URL = "/menu/today";

    @Autowired
    private RestaurantService service;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createWithLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = RESTAURANT_MATCHER.readFromJson(action);
        int id = created.id();
        newRestaurant.setId(id);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(id), newRestaurant);
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createInvalid() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, "", "");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void createDuplicate() throws Exception {
        Restaurant newRestaurant = new Restaurant(null, restaurant2.getName(), "new address");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_RESTAURANT_URL_BASE, RESTAURANT_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void deleteNotFound() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_RESTAURANT_URL_BASE, RESTAURANT_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void deleteForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_RESTAURANT_URL_BASE, RESTAURANT_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_RESTAURANT_URL_BASE, RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_RESTAURANT_URL_BASE, RESTAURANT_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2, restaurant5, restaurant1, restaurant3, restaurant4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST_MENU_TODAY_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2, restaurant5, restaurant1, restaurant3, restaurant4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenuByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST_MENU_BY_DATE_URL)
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2, restaurant5, restaurant1, restaurant3, restaurant4));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getAllWithMenuByDateEmptyList() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST_MENU_BY_DATE_URL)
                .param("date", LocalDate.now().minusDays(1).toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(List.of()));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo(RESTAURANT_ID, "update", "update address");
        perform(MockMvcRequestBuilders.put(REST_RESTAURANT_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT_ID), RestaurantsUtil.createFromTo(updatedTo));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateInvalid() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo(null, "", "");
        perform(MockMvcRequestBuilders.put(REST_RESTAURANT_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateHtmlUnsafe() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo(null, "<script>alert(123)</script>", "<script>alert(123)</script>");
        perform(MockMvcRequestBuilders.put(REST_RESTAURANT_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void updateDuplicate() throws Exception {
        RestaurantTo updatedTo = new RestaurantTo(null, restaurant2.getName(), "new address");
        perform(MockMvcRequestBuilders.put(REST_RESTAURANT_URL_BASE, RESTAURANT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updatedTo)))
                .andDo(print())
                .andExpect(status().isNoContent());

        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_RESTAURANT_URL_BASE, RESTAURANT_ID + REST_MENU_BY_DATE_URL)
                .param("date", LocalDate.now().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(restaurant1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getWithMenuNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_RESTAURANT_URL_BASE + REST_MENU_BY_DATE_URL, RESTAURANT_ID)
                .param("date", LocalDate.now().minusDays(1).toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}