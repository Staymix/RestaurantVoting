package ru.staymix.restaurantvoting.testdata;

import ru.staymix.restaurantvoting.model.Dish;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import java.time.LocalDate;
import java.util.List;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");
    public static final int DISH_ID = 1;
    public static final int DISH_NOT_FOUND = 9999;
    public static final Dish dish1 = new Dish(DISH_ID, "Gouda & Bacon Goughnuts", 15, "Siracha & maple mayo, bacon dust", LocalDate.now());
    public static final Dish dish2 = new Dish(DISH_ID + 1, "Tostadas", 15, "BBQ jackfruit, guacamole, pico de gallo, crisp onion", LocalDate.now());
    public static final Dish dish3 = new Dish(DISH_ID + 2, "Chili and coriander beef short rib", 22, "Thai salad, coriander chermoula", LocalDate.now());
    public static final Dish dish4 = new Dish(DISH_ID + 3, "Potato Gnocchi", 11, "Ceps purée, mushroom ragout, coffee dust", LocalDate.now());
    public static final Dish dish5 = new Dish(DISH_ID + 4, "Black Angus Beef Fillet", 28, "Hen of the woods, roasted Grelot onion, parsley, red wine jus", LocalDate.now());
    public static final Dish dish6 = new Dish(DISH_ID + 5, "Pan-Seared Wild Halibut", 16, "Courgette, young basil, marinated squash blossom", LocalDate.now());
    public static final Dish dish7 = new Dish(DISH_ID + 6, "Gressingham duck", 21, "Smoked duck breast, charred orange, radicchio leaves, pomegranate molasses", LocalDate.now());
    public static final Dish dish8 = new Dish(DISH_ID + 7, "Vegetable broth", 9, "Roasted winter vegetables, pearl barley, wild nettle pesto, toasted sourdough", LocalDate.now());
    public static final Dish dish9 = new Dish(DISH_ID + 8, "Pan seared Orkney scallops", 18, "Cumin and turmeric red lentils, heritage radishes and dandelion leaves", LocalDate.now());
    public static final Dish dish10 = new Dish(DISH_ID + 9, "Meatball Parmesan Sliders", 16, "House Made Meatballs, Provolone, San Marzano Tomato Gravy", LocalDate.now());
    public static final Dish dish11 = new Dish(DISH_ID + 10, "Tuscan Chicken", 18, "8oz Wagyu Patty, Romaine, Tomato, House Made Pickles, Brioche Bun", LocalDate.now());
    public static final Dish dish12 = new Dish(DISH_ID + 11, "Old School Sausage & Peppers", 17, "Fennel Sausage, Caramelized Onions, Roasted Red Pepper, San Marzano Tomato Gravy, Toasted Baguette", LocalDate.now());
    public static final Dish dish13 = new Dish(DISH_ID + 12, "For The Sea Food And Surf & Turf Lover", 45, "Grilled Lobster Whole", LocalDate.now());
    public static final Dish dish14 = new Dish(DISH_ID + 13, "The Westminster’ Beef Flank Sandwich", 26, "Iceberg, Horseradish Crème Fraiche, Piccalilli, Sour Dough", LocalDate.now());
    public static final Dish dish15 = new Dish(DISH_ID + 14, "Gillray’s Wagyu Steak Burger", 28, "Brioche Bun, Smoked Cheddar, Bacon Jam, Pickle, Lettuce, Tomato", LocalDate.now());
    public static final List<Dish> menu1 = List.of(dish3, dish1, dish2);
    public static final List<Dish> menu2 = List.of(dish5, dish6, dish4);
    public static final List<Dish> menu3 = List.of(dish7, dish9, dish8);
    public static final List<Dish> menu4 = List.of(dish10, dish12, dish11);
    public static final List<Dish> menu5 = List.of(dish13, dish15, dish14);

    public static Dish getNew() {
        return new Dish(null, "new", 10, "new description", LocalDate.now());
    }

    public static Dish getUpdated() {
        return new Dish(DISH_ID, "update", 1, "updated", LocalDate.now());
    }
}
