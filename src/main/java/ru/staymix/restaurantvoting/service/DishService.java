package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository repository;

    public Dish create(Dish dish) {
        notNull(dish, "dish must not be null");
        return repository.save(dish);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public Dish get(int id) {
        return repository.getExisted(id);
    }

    public List<Dish> getMenuToday(int restaurantId) {
        return repository.getMenuToday(restaurantId, LocalDate.now());
    }

    public void update(Dish dish) {
        notNull(dish, "dish must not be null");
        repository.save(dish);
    }
}
