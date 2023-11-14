package ru.staymix.restaurantvoting.service;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.repository.DishRepository;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class DishService {
    private final DishRepository repository;
    private final EntityManager em;

    @CacheEvict(value = "restaurantsWithMenu", key = "#dish.dishDate")
    @Transactional
    public Dish create(Dish dish, int restaurantId) {
        notNull(dish, "dish must not be null");
        if (!dish.isNew()) {
            throw new IllegalArgumentException("dish should not have an ID");
        }
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        return repository.save(dish);
    }

    @CacheEvict(value = "restaurantsWithMenu", allEntries = true)
    public void delete(int id, int restaurantId) {
        checkNotFoundWithId(repository.delete(id, restaurantId) != 0, id);
    }

    public Dish get(int id, int restaurantId) {
        return checkNotFoundWithId(repository.findById(id)
                .filter(dish -> dish.getRestaurant().getId() == restaurantId)
                .orElse(null), id);
    }

    public List<Dish> getMenuByDate(int restaurantId, LocalDate date) {
        return repository.getMenuByDate(restaurantId, date);
    }

    @CacheEvict(value = "restaurantsWithMenu", key = "#dish.dishDate")
    @Transactional
    public void update(Dish dish, int restaurantId) {
        notNull(dish, "dish must not be null");
        dish.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        checkNotFoundWithId(repository.save(dish), dish.id());
    }
}
