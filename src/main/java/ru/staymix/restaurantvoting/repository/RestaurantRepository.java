package ru.staymix.restaurantvoting.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends BaseRepository<Restaurant> {

    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu d WHERE r.id=:id AND d.dishDate=:date ORDER BY d.name, d.price")
    Restaurant getWithMenu(int id, LocalDate date);

    @Query("SELECT DISTINCT r FROM Restaurant r LEFT JOIN FETCH r.menu d WHERE d.dishDate=:date ORDER BY r.name, d.name, d.price")
    List<Restaurant> getAllWithMenu(LocalDate date);
}
