package ru.staymix.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id =:restaurantId AND d.dishDate =:dateToday ORDER BY d.name, d.price")
    List<Dish> getMenuToday(int restaurantId, LocalDate dateToday);
}
