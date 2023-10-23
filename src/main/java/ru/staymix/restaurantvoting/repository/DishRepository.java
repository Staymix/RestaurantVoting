package ru.staymix.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id=:id AND d.restaurant.id=:restaurantId")
    int delete(int id, int restaurantId);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id =:restaurantId AND d.dishDate =:date ORDER BY d.name, d.price")
    List<Dish> getMenuFromDate(int restaurantId, LocalDate date);


}
