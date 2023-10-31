package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final int DISH_ID = 1;
    public static final int DISH_NOT_FOUND = 9999;
    public static final Dish dish1 = new Dish(DISH_ID, "Gouda & Bacon Goughnuts", 15, "Siracha & maple mayo, bacon dust", 1688, LocalDate.now());
    public static final Dish dish2 = new Dish(DISH_ID + 1, "Tostadas", 15, "BBQ jackfruit, guacamole, pico de gallo, crisp onion", 1680, LocalDate.now());
    public static final Dish dish3 = new Dish(DISH_ID + 2, "Chili and coriander beef short rib", 22, "Thai salad, coriander chermoula", 544, LocalDate.now());
    public static final Dish dish4 = new Dish(DISH_ID + 3, "Potato Gnocchi", 11, "Ceps purée, mushroom ragout, coffee dust", 1204, LocalDate.now());
    public static final Dish dish5 = new Dish(DISH_ID + 4, "Black Angus Beef Fillet", 28, "BBQ jackfruit, guacamole, pico de gallo, crisp onion", 1680, LocalDate.now());
    public static final Dish dish6 = new Dish(DISH_ID + 5, "Pan-Seared Wild Halibut", 16, "Courgette, young basil, marinated squash blossom", 655, LocalDate.now());
    public static final Dish dish7 = new Dish(DISH_ID + 6, "Gressingham duck", 21, "Smoked duck breast, charred orange, radicchio leaves, pomegranate molasses", 700, LocalDate.now());
    public static final Dish dish8 = new Dish(DISH_ID + 7, "Vegetable broth", 9, "Roasted winter vegetables, pearl barley, wild nettle pesto, toasted sourdough", 652, LocalDate.now());
    public static final Dish dish9 = new Dish(DISH_ID + 8, "Pan seared Orkney scallops", 18, "Cumin and turmeric red lentils, heritage radishes and dandelion leaves", 590, LocalDate.now());
    public static final Dish dish10 = new Dish(DISH_ID + 9, "Meatball Parmesan Sliders", 16, "House Made Meatballs, Provolone, San Marzano Tomato Gravy", 1001, LocalDate.now());
    public static final Dish dish11 = new Dish(DISH_ID + 10, "Tuscan Chicken", 18, "8oz Wagyu Patty, Romaine, Tomato, House Made Pickles, Brioche Bun", 856, LocalDate.now());
    public static final Dish dish12 = new Dish(DISH_ID + 11, "Old School Sausage & Peppers", 17, "Fennel Sausage, Caramelized Onions, Roasted Red Pepper, San Marzano Tomato Gravy, Toasted Baguette", 963, LocalDate.now());
    public static final Dish dish13 = new Dish(DISH_ID + 12, "For The Sea Food And Surf & Turf Lover", 45, "Grilled Lobster Whole", 378, LocalDate.now());
    public static final Dish dish14 = new Dish(DISH_ID + 13, "The Westminster’ Beef Flank Sandwich", 26, "Iceberg, Horseradish Crème Fraiche, Piccalilli, Sour Dough", 737, LocalDate.now());
    public static final Dish dish15 = new Dish(DISH_ID + 14, "Gillray’s Wagyu Steak Burger", 28, "Brioche Bun, Smoked Cheddar, Bacon Jam, Pickle, Lettuce, Tomato", 847, LocalDate.now());
    public static final List<Dish> menu1 = List.of(dish3, dish1, dish2);
    public static final List<Dish> menu2 = List.of(dish4, dish5, dish6);
    public static final List<Dish> menu3 = List.of(dish7, dish8, dish9);
    public static final List<Dish> menu4 = List.of(dish10, dish11, dish12);
    public static final List<Dish> menu5 = List.of(dish13, dish14, dish15);

    public static Dish getNew() {
        return new Dish(null, "new", 10, "new description", 100, LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "update", 1, "updated", 10, LocalDate.now());
    }
}
