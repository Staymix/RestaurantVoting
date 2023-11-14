package ru.staymix.restaurantvoting.service;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.error.IllegalRequestDataException;
import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.repository.VoteRepository;
import ru.staymix.restaurantvoting.util.validation.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static ru.staymix.restaurantvoting.util.VotesUtil.isBeforeTimeLimit;

@Service
@AllArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final EntityManager em;

    @CacheEvict(value = "votes", key = "#userId")
    @Transactional
    public Vote create(int userId, int restaurantId) {
        if (hasVoteTodayByUser(userId, restaurantId)) {
            throw new DataConflictException("Vote already exists for today");
        }
        return repository.save(new Vote(null, LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES), userService.get(userId), restaurantService.get(restaurantId)));
    }

    public Vote getTodayByUser(int userId) {
        return repository.getByUserAndDate(userId, LocalDate.now()).orElse(null);
    }

    @Cacheable(value = "votes", key = "#userId")
    public List<Vote> getAll(int userId) {
        return repository.getAll(userId);
    }

    @CacheEvict(value = "votes", key = "#userId")
    @Transactional
    public void update(int userId, int restaurantId) {
        Vote updated = ValidationUtil.checkNotFound(getTodayByUser(userId), "Vote for today is not found for the user id = " + userId);
        updated.setVoteTime(LocalTime.now().truncatedTo(ChronoUnit.MINUTES));
        if (!isBeforeTimeLimit(updated) || !hasVoteTodayByUser(userId, restaurantId)) {
            throw new IllegalRequestDataException("Voting is over");
        }
        updated.setRestaurant(em.getReference(Restaurant.class, restaurantId));
        repository.save(updated);
    }

    private boolean hasVoteTodayByUser(int userId, Integer restaurantId) {
        Optional<Vote> optional = repository.getByUserAndDate(userId, LocalDate.now());
        if (optional.isPresent()) {
            if (restaurantId.equals(optional.get().getRestaurant().getId())) {
                throw new DataConflictException("Vote for this restaurant id = " + restaurantId + " already exists");
            }
            return true;
        }
        return false;
    }
}
