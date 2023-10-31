package ru.staymix.restaurantvoting.web.restaurant;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.to.RestaurantTo;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController extends AbstractRestaurantController {
    public static final String REST_URL = "/api/admin/restaurants";

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody RestaurantTo restaurantTo) {
        Restaurant created = super.create(restaurantTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}").build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public RestaurantTo get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<RestaurantTo> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/menu/today")
    public List<Restaurant> getAllWithMenuToday() {
        return super.getAllWithMenuToday();
    }

    @Override
    @GetMapping("/menu/from-date")
    public List<Restaurant> getAllWithMenuFromDate(@RequestParam LocalDate date) {
        return super.getAllWithMenuFromDate(date);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody RestaurantTo restaurantTo, @PathVariable int id) {
        super.update(restaurantTo, id);
    }

    @Override
    @GetMapping("/{id}/menu/from-date")
    public Restaurant getWithMenu(@PathVariable int id, @RequestParam LocalDate date) {
        return super.getWithMenu(id, date);
    }
}
