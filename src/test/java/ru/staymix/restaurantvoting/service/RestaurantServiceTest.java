package ru.staymix.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Restaurant;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;

class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int id = created.id();
        Restaurant newRest = getNew();
        newRest.setId(id);
        RESTAURANT_MATCHER.assertMatch(created, newRest);
        RESTAURANT_MATCHER.assertMatch(service.get(id), newRest);
    }

    @Test
    void delete() {
        service.delete(REST_ID);
        assertThrows(NotFoundException.class, () -> service.get(REST_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(REST_NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(REST_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(REST_NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Restaurant> restaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(restaurants, restaurant2, restaurant5, restaurant1, restaurant3, restaurant4);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(updated, service.get(REST_ID));
    }

    @Test
    void getWithMenu() {
        Restaurant actual = service.getWithMenu(REST_ID);
        RESTAURANT_WITH_MENU_MATCHER.assertMatch(actual, restaurant1);
    }

    @Test
    void getWithMenuNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithMenu(REST_NOT_FOUND));
    }
}