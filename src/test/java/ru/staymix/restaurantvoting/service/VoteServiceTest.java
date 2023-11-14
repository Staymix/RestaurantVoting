package ru.staymix.restaurantvoting.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.staymix.restaurantvoting.error.DataConflictException;
import ru.staymix.restaurantvoting.model.Vote;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.RESTAURANT5_ID;
import static ru.staymix.restaurantvoting.testdata.UserTestData.ADMIN_ID;
import static ru.staymix.restaurantvoting.testdata.UserTestData.USER_ID;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.*;

class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void create() {
        Vote created = service.create(ADMIN_ID, RESTAURANT5_ID);
        int id = created.id();
        Vote newVote = getNew();
        newVote.setId(id);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(service.getTodayByUser(ADMIN_ID), newVote);
    }

    @Test
    void createDuplicate() {
        assertThrows(DataConflictException.class, () -> service.create(USER_ID, RESTAURANT5_ID));
    }

    @Test
    void getTodayByUser() {
        Vote vote = service.getTodayByUser(USER_ID);
        VOTE_MATCHER.assertMatch(vote, vote1);
    }

    @Test
    void getAll() {
        List<Vote> votes = service.getAll(USER_ID);
        VOTE_MATCHER.assertMatch(votes, vote1);
    }
}