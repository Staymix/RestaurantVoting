package ru.staymix.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.error.IllegalRequestDataException;
import ru.staymix.restaurantvoting.error.NotFoundException;
import ru.staymix.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.RESTAURANT2_ID;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.restaurant2;
import static ru.staymix.restaurantvoting.testdata.UserTestData.*;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.getNew;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.getUpdated;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void create() {
        Vote created = service.create(getNew(), ADMIN_ID);
        int id = created.id();
        Vote newVote = getNew();
        newVote.setId(id);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.getTodayByUser(ADMIN_ID), newVote);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataConflictException.class, () -> service.create(getNew(), USER_ID));
    }

    @Test
    void getTodayByUser() {
        Vote vote = service.getTodayByUser(USER_ID);
        VOTE_MATCHER.assertMatch(vote, vote1);
    }

    @Test
    void getTodayByUserNotFound() {
        assertThrows(NotFoundException.class, () -> service.getTodayByUser(USER_NOT_FOUND));
    }

    @Test
    void getAll() {
        List<Vote> votes = service.getAll(USER_ID);
        VOTE_MATCHER.assertMatch(votes, vote1);
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        service.update(updated, USER_ID, RESTAURANT2_ID);
        VOTE_MATCHER.assertMatch(service.getTodayByUser(USER_ID), updated);
    }

    @Test
    void updateAfterTimeLimit() {
        Vote updated = new Vote(VOTE_ID, LocalDate.now(), TIME_AFTER_UPDATE_LIMIT, user, restaurant2);
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, USER_ID, RESTAURANT2_ID));
    }

    @Test
    void updateHasNotToday() {
        Vote updated = new Vote(VOTE_ID, LocalDate.now(), TIME_BEFORE_TODAY_VOTING, admin, restaurant2);
        assertThrows(IllegalRequestDataException.class, () -> service.update(updated, ADMIN_ID, RESTAURANT2_ID));
    }
}