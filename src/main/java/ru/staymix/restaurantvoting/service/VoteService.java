package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.repository.VoteRepository;

import java.util.List;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class VoteService {
    private static final Sort SORT_DATE = Sort.by(Sort.Direction.ASC, "date");

    private final VoteRepository repository;

    public Vote create(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        return repository.save(vote);
    }

    public Vote get(int id, int userId) {
        return repository.findById(id)
                .filter(vote -> vote.getUser().id() == userId)
                .orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return repository.findAll(SORT_DATE);
    }

    public void update(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        checkNotFoundWithId(repository.save(vote), vote.id());
    }
}
