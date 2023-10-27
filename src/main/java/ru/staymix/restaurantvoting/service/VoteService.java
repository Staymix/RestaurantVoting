package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.error.IllegalRequestDataException;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.repository.VoteRepository;

import java.util.List;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.VotesUtil.isBeforeTimeLimit;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;

    public Vote create(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        if (hasVoteTodayByUser(userId)) {
            throw new DataConflictException("Vote already exists for today");
        }
        return repository.save(vote);
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.findById(id)
                .filter(vote -> vote.getUser().id() == userId)
                .orElse(null), id);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        if (!isBeforeTimeLimit(vote) || !hasVoteTodayByUser(userId)) {
            throw new IllegalRequestDataException("Voting is over");
        }
        repository.save(vote);
    }

    private boolean hasVoteTodayByUser(int userId) {
        return repository.getTodayByUser(userId).isPresent();
    }
}
