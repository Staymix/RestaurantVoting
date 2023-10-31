package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.repository.RestaurantRepository;
import ru.staymix.restaurantvoting.to.RestaurantTo;
import ru.staymix.restaurantvoting.util.RestaurantsUtil;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.Assert.notNull;

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

    public List<Restaurant> getAllWithMenu(LocalDate date) {
        return repository.getAllWithMenu(date);
    }

    public void update(RestaurantTo restaurantTo) {
        Restaurant restaurant = get(restaurantTo.id());
        repository.save(RestaurantsUtil.updateFromTo(restaurant, restaurantTo));
    }

    public Restaurant getWithMenu(int id, LocalDate date) {
        Restaurant restaurant = repository.getWithMenu(id, date);
        if (restaurant == null) {
            throw new NotFoundException("Menu is not found for restaurant id = " + id + " on the date " + date + ".");
        }
        return restaurant;
    }
}
