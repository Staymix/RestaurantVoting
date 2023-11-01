package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.error.IllegalRequestDataException;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.repository.VoteRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.notNull;
import static ru.staymix.restaurantvoting.util.VotesUtil.isBeforeTimeLimit;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final RestaurantService restaurantService;

    @CacheEvict(value = "votes", allEntries = true)
    public Vote create(Vote vote, int userId) {
        notNull(vote, "vote must not be null");
        if (hasVoteTodayByUser(userId, vote.getRestaurant().getId())) {
            throw new DataConflictException("Vote already exists for today");
        }
        return repository.save(vote);
    }

    public Vote getTodayByUser(int userId) {
        return repository.getTodayByUser(userId)
                .orElseThrow(() -> new NotFoundException("Vote for today is not found for the user id = " + userId));
    }

    @Cacheable("votes")
    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @Transactional
    public void update(Vote vote, int userId, Integer restaurantId) {
        notNull(vote, "vote must not be null");
        if (!isBeforeTimeLimit(vote) || !hasVoteTodayByUser(userId, restaurantId)) {
            throw new IllegalRequestDataException("Voting is over");
        }
        vote.setRestaurant(restaurantService.get(restaurantId));
        repository.save(vote);
    }

    private boolean hasVoteTodayByUser(int userId, Integer restaurantId) {
        Optional<Vote> optional = repository.getTodayByUser(userId);
        if (optional.isPresent()) {
            if (restaurantId.equals(optional.get().getRestaurant().getId())) {
                throw new DataConflictException("Vote for this restaurant id = " + restaurantId + " already exists");
            }
            return true;
        }
        return false;
    }
}
