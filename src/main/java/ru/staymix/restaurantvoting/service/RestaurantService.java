package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.repository.RestaurantRepository;

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

    public void update(Restaurant restaurant) {
        notNull(restaurant, "restaurant must not be null");
        repository.save(restaurant);
    }

    public Restaurant getWithMenu(int id) {
        return checkNotFoundWithId(repository.getWithMenu(id), id);
    }
}
