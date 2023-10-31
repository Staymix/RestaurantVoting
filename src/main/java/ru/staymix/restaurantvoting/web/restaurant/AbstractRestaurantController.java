package ru.staymix.restaurantvoting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.service.RestaurantService;
import ru.staymix.restaurantvoting.to.RestaurantTo;
import ru.staymix.restaurantvoting.util.RestaurantsUtil;

import java.time.LocalDate;
import java.util.List;

import static ru.staymix.restaurantvoting.util.RestaurantsUtil.createTo;
import static ru.staymix.restaurantvoting.util.RestaurantsUtil.getTos;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractRestaurantController {
    @Autowired
    protected RestaurantService service;

    public Restaurant create(RestaurantTo restaurantTo) {
        log.info("create {}", restaurantTo);
        checkNew(restaurantTo);
        return service.create(RestaurantsUtil.createNewFromTo(restaurantTo));
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

    public void update(RestaurantTo restaurantTo, int id) {
        log.info("update {} with id={}", restaurantTo, id);
        assureIdConsistent(restaurantTo, id);
        service.update(restaurantTo);
    }

    public Restaurant getWithMenu(int id, LocalDate date) {
        log.info("getWithMenu id={} for date={}", id, date);
        return service.getWithMenu(id, date);
    }
}
