package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.to.RestaurantTo;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.staymix.restaurantvoting.testdata.DishTestData.*;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> RESTAURANT_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "menu");
    public static final MatcherFactory.Matcher<RestaurantTo> RESTAURANT_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RestaurantTo.class);
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
    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT_ID, "Sabine", "St. Paul's, 10 Godliman St, London, England, United Kingdom, 7");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT2_ID, "Aqua Shard", "Level 31, The Shard, 31 St Thomas St, London SE1 9RY, United Kingdom");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT3_ID, "Searcys at The Gherkin", "The Gherkin, 30 St Mary Axe, London EC3A 8BF, United Kingdom");
    public static final Restaurant restaurant4 = new Restaurant(RESTAURANT4_ID, "Tavolino", "2 More London Pl, London SE1 2RR, United Kingdom");
    public static final Restaurant restaurant5 = new Restaurant(RESTAURANT5_ID, "Gillray’s Steakhouse", "County Hall, Westminster Bridge Rd, London SE1 7PB, United Kingdom");
    public static final Restaurant restaurant1WithoutMenu = new Restaurant(RESTAURANT_ID, "Sabine", "St. Paul's, 10 Godliman St, London, England, United Kingdom, 7");

    static {
        restaurant1.setMenu(menu1);
        restaurant2.setMenu(menu2);
        restaurant3.setMenu(menu3);
        restaurant4.setMenu(menu4);
        restaurant5.setMenu(menu5);
        restaurant1WithoutMenu.setMenu(List.of());
    }

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", "New address");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT_ID, "Update Restaurant", "Update address");
    }
}
