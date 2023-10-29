package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.repository.RestaurantRepository;
import ru.staymix.restaurantvoting.to.RestaurantTo;
import ru.staymix.restaurantvoting.util.RestaurantsUtil;

import java.util.Comparator;
import java.util.List;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class RestaurantService {
    private static final Sort SORT_NAME = Sort.by(Sort.Direction.ASC, "name");

    private final RestaurantRepository repository;

    public Restaurant create(Restaurant restaurant) {
        notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public Restaurant get(int id) {
        return repository.getExisted(id);
    }

    public List<Restaurant> getAll() {
        return repository.findAll(SORT_NAME);
    }

    public void update(RestaurantTo restaurantTo) {
        Restaurant restaurant = get(restaurantTo.id());
        repository.save(RestaurantsUtil.updateFromTo(restaurant, restaurantTo));
    }

    public Restaurant getWithMenu(int id) {
        Restaurant restaurant = repository.getWithMenu(id);
        checkNotFoundWithId(restaurant, id);
        restaurant.setMenu(sortMenu(restaurant.getMenu()));
        return restaurant;
    }

    private List<Dish> sortMenu(List<Dish> menu) {
        return menu.stream()
                .sorted(Comparator.comparing(Dish::getName))
                .toList();
    }
}
