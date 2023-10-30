package ru.staymix.restaurantvoting.web.vote;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.staymix.restaurantvoting.service.VoteService;
import ru.staymix.restaurantvoting.testdata.UserTestData;
import ru.staymix.restaurantvoting.to.VoteTo;
import ru.staymix.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.RESTAURANT2_ID;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.RESTAURANT5_ID;
import static ru.staymix.restaurantvoting.testdata.UserTestData.*;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.getUpdated;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.*;
import static ru.staymix.restaurantvoting.util.VotesUtil.TIME_LIMIT;
import static ru.staymix.restaurantvoting.util.VotesUtil.createTo;
import static ru.staymix.restaurantvoting.web.vote.VoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    @Autowired
    private VoteService service;

    @Test
    @WithUserDetails(UserTestData.ADMIN_MAIL)
    void createWithLocation() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT5_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int id = created.id();
        VoteTo newVoteTo = getNewTo();
        newVoteTo.setId(id);
        VOTE_TO_MATCHER.assertMatch(created, newVoteTo);
        VOTE_TO_MATCHER.assertMatch(createTo(service.getTodayByUser(UserTestData.ADMIN_ID)), newVoteTo);
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void createDuplicate() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT5_ID))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of(voteTo1)));
    }

    @Test
    @WithUserDetails(ADMIN_MAIL)
    void getAllEmptyList() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(List.of()));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void update() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT2_ID))
                .contentType(MediaType.APPLICATION_JSON));
        VoteTo updated = createTo(getUpdated());
        if (LocalTime.now().isBefore(TIME_LIMIT)) {
            action.andExpect(status().isNoContent());
        } else {
            action.andExpect(status().isUnprocessableEntity());
            updated = voteTo1;
        }
        VOTE_TO_MATCHER.assertMatch(createTo(service.getTodayByUser(USER_ID)), updated);
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void updateDuplicate() throws Exception {
        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL)
                .param("restaurantId", String.valueOf(vote1.getRestaurant().getId()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
        if (LocalTime.now().isBefore(TIME_LIMIT)) {
            action.andExpect(status().isConflict());
        } else {
            action.andExpect(status().isUnprocessableEntity());
        }
        VOTE_TO_MATCHER.assertMatch(createTo(service.getTodayByUser(USER_ID)), voteTo1);
    }
}