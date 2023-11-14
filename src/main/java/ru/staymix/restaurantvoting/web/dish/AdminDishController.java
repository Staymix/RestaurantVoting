package ru.staymix.restaurantvoting.web.dish;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.service.DishService;
import ru.staymix.restaurantvoting.web.restaurant.AdminRestaurantController;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
@AllArgsConstructor
public class AdminDishController {
    static final String REST_URL = AdminRestaurantController.REST_URL + "/{restaurantId}/dishes";
    protected DishService service;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@RequestBody Dish dish, @PathVariable int restaurantId) {
        log.info("create {} from restaurant id={}", dish, restaurantId);
        checkNew(dish);
        Dish created = service.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("delete id={} from restaurant id={}", id, restaurantId);
        service.delete(id, restaurantId);
    }

    @GetMapping("/{id}")
    public Dish get(@PathVariable int id, @PathVariable int restaurantId) {
        log.info("get id={} from restaurant id={}", id, restaurantId);
        return service.get(id, restaurantId);
    }

    @GetMapping
    public List<Dish> getMenuToday(@PathVariable int restaurantId) {
        log.info("getMenuToday in restaurant id={}", restaurantId);
        return service.getMenuFromDate(restaurantId, LocalDate.now());
    }

    @GetMapping("/by-date")
    public List<Dish> getMenuFromDate(@PathVariable int restaurantId, @RequestParam LocalDate date) {
        log.info("getMenuFromDate in restaurant id={} from date {}", restaurantId, date);
        return service.getMenuFromDate(restaurantId, date);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        log.info("update {} id={} from restaurant id={}", dish, id, restaurantId);
        assureIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }
}
