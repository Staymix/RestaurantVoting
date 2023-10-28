package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Vote;
import ru.staymix.restaurantvoting.to.VoteTo;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;
import static ru.staymix.restaurantvoting.testdata.UserTestData.*;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> VOTE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");
    public static final MatcherFactory.Matcher<VoteTo> VOTE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(VoteTo.class);
    public static int VOTE_ID = 1;
    public static int VOTE_NOT_FOUND = 999;
    public static Vote vote1 = new Vote(VOTE_ID, LocalDate.now(), LocalTime.of(8, 0), user, restaurant3);
    public static Vote vote2 = new Vote(VOTE_ID + 1, LocalDate.now(), LocalTime.of(9, 0), user5, restaurant3);
    public static Vote vote3 = new Vote(VOTE_ID + 2, LocalDate.now(), LocalTime.of(10, 0), user4, restaurant4);
    public static Vote vote4 = new Vote(VOTE_ID + 3, LocalDate.now(), LocalTime.of(11, 0), user2, restaurant1);
    public static Vote vote5 = new Vote(VOTE_ID + 4, LocalDate.now(), LocalTime.of(12, 0), user3, restaurant2);
    public static VoteTo voteTo1 = new VoteTo(VOTE_ID, RESTAURANT3_ID, LocalDate.now());
    public static VoteTo voteTo2 = new VoteTo(VOTE_ID + 1, RESTAURANT3_ID, LocalDate.now());
    public static VoteTo voteTo3 = new VoteTo(VOTE_ID + 2, RESTAURANT4_ID, LocalDate.now());
    public static VoteTo voteTo4 = new VoteTo(VOTE_ID + 3, RESTAURANT_ID, LocalDate.now());
    public static VoteTo voteTo5 = new VoteTo(VOTE_ID + 4, RESTAURANT2_ID, LocalDate.now());

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), LocalTime.now().truncatedTo(ChronoUnit.MINUTES), admin, restaurant5);
    }

    public static VoteTo getNewTo() {
        return new VoteTo(null, RESTAURANT5_ID, LocalDate.now());
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now(), LocalTime.of(10, 0), user, restaurant2);
    }
}
