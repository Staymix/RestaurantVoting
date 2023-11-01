package ru.staymix.restaurantvoting.web.restaurant;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.staymix.restaurantvoting.web.AbstractControllerTest;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;
import static ru.staymix.restaurantvoting.testdata.UserTestData.USER_MAIL;
import static ru.staymix.restaurantvoting.web.restaurant.AdminRestaurantControllerTest.REST_MENU_TODAY_URL;
import static ru.staymix.restaurantvoting.web.restaurant.UserRestaurantController.REST_URL;

class UserRestaurantControllerTest extends AbstractControllerTest {

    private static final String REST_RESTAURANT_URL_BASE = REST_URL + "/{restaurantId}";

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAllWithMenuToday() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REST_MENU_TODAY_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant2, restaurant5, restaurant1, restaurant3, restaurant4));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenu() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_RESTAURANT_URL_BASE + REST_MENU_TODAY_URL, RESTAURANT_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_WITH_MENU_MATCHER.contentJson(restaurant1));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getWithMenuNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_RESTAURANT_URL_BASE + REST_MENU_TODAY_URL, RESTAURANT_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}