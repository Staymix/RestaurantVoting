package ru.staymix.restaurantvoting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId ORDER BY v.voteDate DESC")
    List<Vote> getAll(int userId);

    @Query("SELECT v FROM Vote v WHERE v.user.id =:userId AND v.voteDate =:date")
    Optional<Vote> getByUserAndDate(int userId, LocalDate date);
}
