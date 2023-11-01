package ru.staymix.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;
import static ru.staymix.restaurantvoting.util.RestaurantsUtil.createTo;

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
        service.delete(RESTAURANT_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(RESTAURANT_NOT_FOUND));
    }

    @Test
    void get() {
        Restaurant restaurant = service.get(RESTAURANT_ID);
        RESTAURANT_MATCHER.assertMatch(restaurant, restaurant1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT_NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Restaurant> restaurants = service.getAll();
        RESTAURANT_MATCHER.assertMatch(restaurants, restaurant2, restaurant5, restaurant1, restaurant3, restaurant4);
    }

    @Test
    void getAllWithMenuToday() {
        List<Restaurant> restaurants = service.getAllWithMenuByDate(LocalDate.now());
        RESTAURANT_MATCHER.assertMatch(restaurants, restaurant2, restaurant5, restaurant1, restaurant3, restaurant4);
    }

    @Test
    void getAllWithMenuByDateEmptyList() {
        List<Restaurant> restaurants = service.getAllWithMenuByDate(LocalDate.now().minusDays(1));
        RESTAURANT_MATCHER.assertMatch(restaurants, List.of());
    }

    @Test
    void update() {
        RestaurantTo updated = createTo(getUpdated());
        service.update(updated);
        RESTAURANT_TO_MATCHER.assertMatch(updated, createTo(service.get(RESTAURANT_ID)));
    }

    @Test
    void getWithMenuByDate() {
        Restaurant actual = service.getWithMenuByDate(RESTAURANT_ID, LocalDate.now());
        RESTAURANT_WITH_MENU_MATCHER.assertMatch(actual, restaurant1);
    }

    @Test
    void getWithMenuByDateNotFound() {
        assertThrows(NotFoundException.class,
                () -> service.getWithMenuByDate(RESTAURANT_ID, LocalDate.now().minusDays(1)));
    }
}