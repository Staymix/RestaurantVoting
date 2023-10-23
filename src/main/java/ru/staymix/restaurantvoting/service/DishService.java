package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository repository;
    private final RestaurantService restaurantService;

    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        notNull(dish, "dish must not be null");
        if (!dish.isNew()) {
            throw new DataConflictException("dish already exists");
        }
        dish.setRestaurant(restaurantService.get(restaurantId));
        return repository.save(dish);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId) != 0, id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.findById(id)
                .filter(dish -> dish.getRestaurant().id() == restaurantId)
                .orElse(null), id);
    }

    public List<Dish> getMenuFromDate(int restaurantId, LocalDate date) {
        return repository.getMenuFromDate(restaurantId, date);
    }

    @Transactional
    public void update(Dish dish, int restaurantId) {
        notNull(dish, "dish must not be null");
        dish.setRestaurant(restaurantService.get(restaurantId));
        checkNotFoundWithId(repository.save(dish), dish.id());
    }
}
