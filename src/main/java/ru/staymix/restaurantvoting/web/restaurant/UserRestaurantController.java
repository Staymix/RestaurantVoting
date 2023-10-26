package ru.staymix.restaurantvoting.web.restaurant;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.to.RestaurantTo;

import java.util.List;

@RestController
@RequestMapping(value = UserRestaurantController.REST_URL, consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserRestaurantController extends AbstractRestaurantController {
    static final String REST_URL = "/api/user/restaurants";

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
    @GetMapping("/{id}/menu")
    public Restaurant getWithMenu(@PathVariable int id) {
        return super.getWithMenu(id);
    }
}