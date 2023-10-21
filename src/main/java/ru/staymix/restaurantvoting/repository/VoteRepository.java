package ru.staymix.restaurantvoting.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.model.Vote;

@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
}
