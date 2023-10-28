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
import ru.staymix.restaurantvoting.util.JsonUtil;
import ru.staymix.restaurantvoting.web.AbstractControllerTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.RESTAURANT5_ID;
import static ru.staymix.restaurantvoting.testdata.UserTestData.USER_ID;
import static ru.staymix.restaurantvoting.testdata.UserTestData.USER_MAIL;
import static ru.staymix.restaurantvoting.testdata.VoteTestData.*;
import static ru.staymix.restaurantvoting.util.VotesUtil.TIME_LIMIT;
import static ru.staymix.restaurantvoting.util.VotesUtil.createTo;
import static ru.staymix.restaurantvoting.web.vote.VoteController.REST_URL;

class VoteControllerTest extends AbstractControllerTest {

    private static final String REST_URL_SLASH = REST_URL + '/';

    @Autowired
    private VoteService service;

    @Test
    @WithUserDetails(UserTestData.ADMIN_MAIL)
    void createWithLocation() throws Exception {
        VoteTo newVote = getNewTo();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newVote)))
                .andExpect(status().isCreated());

        VoteTo created = VOTE_TO_MATCHER.readFromJson(action);
        int id = created.id();
        newVote.setId(id);
        VOTE_TO_MATCHER.assertMatch(created, newVote);
        VOTE_TO_MATCHER.assertMatch(createTo(service.get(id, UserTestData.ADMIN_ID)), newVote);
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void createDuplicate() throws Exception {
        VoteTo duplicate = new VoteTo(null, RESTAURANT5_ID, LocalDate.now());
        perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(duplicate)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(VOTE_TO_MATCHER.contentJson(voteTo1));
    }

    @Test
    @WithUserDetails(USER_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH + VOTE_NOT_FOUND))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_SLASH))
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
    @WithUserDetails(USER_MAIL)
    void update() throws Exception {
        VoteTo updated = createTo(getUpdated());
        ResultActions action = perform(MockMvcRequestBuilders.put(REST_URL_SLASH + VOTE_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)));
        if (LocalTime.now().isBefore(TIME_LIMIT)) {
            action.andExpect(status().isNoContent());
        } else {
            action.andExpect(status().isUnprocessableEntity());
        }
        VOTE_TO_MATCHER.assertMatch(createTo(service.get(VOTE_ID, USER_ID)), updated);
    }
}