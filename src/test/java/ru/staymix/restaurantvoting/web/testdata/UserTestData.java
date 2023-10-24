package ru.staymix.restaurantvoting.web.testdata;

import ru.staymix.restaurantvoting.model.Role;
import ru.staymix.restaurantvoting.model.User;
import ru.staymix.restaurantvoting.util.JsonUtil;
import ru.staymix.restaurantvoting.web.MatcherFactory;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> USER_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 100;
    public static final String USER_MAIL = "user@yandex.ru";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";

    public static final User user = new User(USER_ID, "User", USER_MAIL, "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", ADMIN_MAIL, "admin", Role.ADMIN, Role.USER);
    public static final User guest = new User(GUEST_ID, "Guest", GUEST_MAIL, "guest");
    public static final User user2 = new User(GUEST_ID + 1, "User2", "user2@yandex.ru", "password2", Role.USER);
    public static final User user3 = new User(GUEST_ID + 2, "User3", "user3@yandex.ru", "password3", Role.USER);
    public static final User user4 = new User(GUEST_ID + 3, "User4", "user4@yandex.ru", "password4", Role.USER);
    public static final User user5 = new User(GUEST_ID + 4, "User5", "user5@yandex.ru", "password5", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        return new User(USER_ID, "UpdatedName", USER_MAIL, "newPass", false, new Date(), Collections.singleton(Role.ADMIN));
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
