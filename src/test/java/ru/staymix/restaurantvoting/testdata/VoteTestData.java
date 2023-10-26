package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.staymix.restaurantvoting.testdata.RestaurantTestData.*;

public class VoteTestData {
    public static int VOTE_ID = 1;
    public static int NOT_FOUND = 999;
    public static Vote vote1 = new Vote(VOTE_ID, LocalDate.now(), LocalTime.of(8, 0), UserTestData.user, restaurant3);
    public static Vote vote2 = new Vote(VOTE_ID + 1, LocalDate.now(), LocalTime.of(9, 0), UserTestData.user5, restaurant3);
    public static Vote vote3 = new Vote(VOTE_ID + 2, LocalDate.now(), LocalTime.of(10, 0), UserTestData.user4, restaurant4);
    public static Vote vote4 = new Vote(VOTE_ID + 3, LocalDate.now(), LocalTime.of(11, 0), UserTestData.user2, restaurant1);
    public static Vote vote5 = new Vote(VOTE_ID + 4, LocalDate.now(), LocalTime.of(12, 0), UserTestData.user3, restaurant2);

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), LocalTime.now(), UserTestData.user2, restaurant5);
    }

    public static Vote getUpdated() {
        return new Vote(VOTE_ID, LocalDate.now(), LocalTime.now(), UserTestData.user, restaurant2);
    }
}
