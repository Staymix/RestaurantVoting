package ru.staymix.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.service.RestaurantService;
import ru.staymix.restaurantvoting.to.RestaurantTo;

import java.util.List;

import static ru.staymix.restaurantvoting.util.RestaurantsUtil.createTo;
import static ru.staymix.restaurantvoting.util.RestaurantsUtil.getTos;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractRestaurantController {
    @Autowired
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

    public RestaurantTo get(int id) {
        log.info("get {}", id);
        return createTo(service.get(id));
    }

    public List<RestaurantTo> getAll() {
        log.info("getAll");
        return getTos(service.getAll());
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
