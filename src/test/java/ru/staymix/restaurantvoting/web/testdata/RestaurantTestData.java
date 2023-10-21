package ru.staymix.restaurantvoting.web.testdata;

import ru.staymix.restaurantvoting.model.Restaurant;

import java.util.List;

import static ru.staymix.restaurantvoting.web.testdata.DishTestData.*;

public class RestaurantTestData {
    public static final int REST_ID = 1;
    public static final int NOT_FOUND = 9999;
    public static final Restaurant restaurant1 = new Restaurant(REST_ID, "Sabine", menu1);
    public static final Restaurant restaurant2 = new Restaurant(REST_ID + 1, "Aqua Shard", menu2);
    public static final Restaurant restaurant3 = new Restaurant(REST_ID + 2, "Searcys at The Gherkin", menu3);
    public static final Restaurant restaurant4 = new Restaurant(REST_ID + 3, "Tavolino", menu4);
    public static final Restaurant restaurant5 = new Restaurant(REST_ID + 4, "Gillray’s Steakhouse", menu5);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", List.of());
    }

    public static Restaurant getUpdated() {
        return new Restaurant(null, "Update Restaurant", List.of());
    }
}
