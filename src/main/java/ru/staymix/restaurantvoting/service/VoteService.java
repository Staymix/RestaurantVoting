package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.error.IllegalRequestDataException;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.repository.VoteRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.validation.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;

    public Vote create(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        if (hasVoteToday(vote.id(), userId)) {
            throw new DataConflictException("Vote already exists for today");
        }
        return repository.save(vote);
    }

    public Vote get(int id, int userId) {
        return repository.findById(id)
                .filter(vote -> vote.getUser().id() == userId)
                .orElse(null);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        if (isBeforeTimeLimit(vote.id(), userId)) {
            throw new IllegalRequestDataException("Voting is over");
        }
        checkNotFoundWithId(repository.save(vote), vote.id());
    }

    private boolean hasVoteToday(int id, int userId) {
        return getToday(id, userId).isPresent();
    }

    private boolean isBeforeTimeLimit(int id, int userId) {
        Optional<Vote> vote = getToday(id, userId);
        return vote.map(value -> value.getVoteTime().isBefore(LocalTime.of(11, 0))).orElse(false);
    }

    private Optional<Vote> getToday(int id, int userId) {
        return repository.getToday(id, userId);
    }
}
