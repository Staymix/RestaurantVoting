package ru.staymix.restaurantvoting.util;

import ru.staymix.restaurantvoting.model.Restaurant;
import ru.staymix.restaurantvoting.to.RestaurantTo;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantsUtil {
    public static List<RestaurantTo> getTos(Collection<Restaurant> restaurants) {
        return restaurants.stream()
                .map(RestaurantsUtil::createTo)
                .collect(Collectors.toList());
    }

    public static RestaurantTo createTo(Restaurant restaurant) {
        return new RestaurantTo(restaurant.id(), restaurant.getName(), restaurant.getAddress());
    }

    public static Restaurant createNewFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(null, restaurantTo.getName(), restaurantTo.getAddress());
    }

    public static Restaurant createFromTo(RestaurantTo restaurantTo) {
        return new Restaurant(restaurantTo.getId(), restaurantTo.getName(), restaurantTo.getAddress());
    }
}
