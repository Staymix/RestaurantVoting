package ru.staymix.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.staymix.restaurantvoting.testdata.DishTestData.*;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.REST2_ID;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.REST_ID;

class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Test
    void create() {
        Dish created = service.create(getNew(), REST_ID);
        int id = created.id();
        Dish newDish = getNew();
        newDish.setId(id);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(service.get(id, REST_ID), newDish);
    }

    @Test
    void delete() {
        service.delete(DISH_ID, REST_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH_ID, REST_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(DISH_NOT_FOUND, REST_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(DISH_ID, REST2_ID));
    }

    @Test
    void get() {
        Dish dish = service.get(DISH_ID, REST_ID);
        DISH_MATCHER.assertMatch(dish, dish1);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(DISH_NOT_FOUND, REST_ID));
    }

    @Test
    void getMenuFromDate() {
        List<Dish> menu = service.getMenuFromDate(REST_ID, LocalDate.now().minusDays(1));
        DISH_MATCHER.assertMatch(menu, menu1);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, REST_ID);
        DISH_MATCHER.assertMatch(service.get(DISH_ID, REST_ID), updated);
    }
}