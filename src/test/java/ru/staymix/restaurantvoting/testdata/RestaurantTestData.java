package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.staymix.restaurantvoting.testdata.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static MatcherFactory.Matcher<Restaurant> RESTAURANT_WITH_MENU_MATCHER =
            MatcherFactory.usingAssertions(Restaurant.class,
                    (a, e) -> assertThat(a).usingRecursiveComparison().ignoringFields("menu.restaurant").isEqualTo(e),
                    (a, e) -> {
                        throw new UnsupportedOperationException();
                    });
    public static final int RESTAURANT_ID = 1;
    public static final int RESTAURANT2_ID = 2;
    public static final int RESTAURANT3_ID = 3;
    public static final int RESTAURANT4_ID = 4;
    public static final int RESTAURANT5_ID = 5;
    public static final int RESTAURANT_NOT_FOUND = 9999;
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Sabine");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Aqua Shard");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Searcys at The Gherkin");
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT4_ID, "Tavolino");
    public static final Restaurant restaurant5 = new Restaurant(RESTAURANT5_ID, "Gillray’s Steakhouse");

    static {
        restaurant1.setMenu(menu1);
        restaurant2.setMenu(menu2);
        restaurant3.setMenu(menu3);
        restaurant4.setMenu(menu4);
        restaurant5.setMenu(menu5);
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "Update Restaurant");
    }
}
