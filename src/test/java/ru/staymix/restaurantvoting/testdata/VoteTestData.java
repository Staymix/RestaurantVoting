package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.to.VoteTo;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;
import static ru.staymix.restaurantvoting.testdata.UserTestData.admin;
import static ru.staymix.restaurantvoting.testdata.UserTestData.user;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);
    public static int VOTE_ID = 1;
    public static final LocalTime TIME_AFTER_UPDATE_LIMIT = LocalTime.of(11, 0);
    public static final LocalTime TIME_BEFORE_TODAY_VOTING = LocalTime.of(10, 59);
    public static Vote vote1 = new Vote(VOTE_ID, LocalDate.now(), LocalTime.of(8, 0), user, restaurant3);
    public static VoteTo voteTo1 = new VoteTo(VOTE_ID, RESTAURANT3_ID, LocalDate.now());

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES), admin, restaurant5);
    }

    public static VoteTo getNewTo() {
        return new VoteTo(null, RESTAURANT5_ID, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now(), TIME_BEFORE_TODAY_VOTING, user, restaurant2);
    }
}
