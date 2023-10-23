package ru.staymix.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.service.RestaurantService;

import java.util.List;

import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractRestaurantController {
    protected RestaurantService service;

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    public Restaurant getWithMenu(int id) {
        log.info("getWithMenu {}", id);
        return service.getWithMenu(id);
    }
}
