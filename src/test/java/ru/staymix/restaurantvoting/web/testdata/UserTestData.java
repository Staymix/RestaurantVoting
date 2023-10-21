package ru.staymix.restaurantvoting.web.testdata;

import ru.staymix.restaurantvoting.model.Role;
import ru.staymix.restaurantvoting.model.User;

import java.util.Collections;
import java.util.Date;

public class UserTestData {
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;
    public static final int GUEST_ID = 3;
    public static final int NOT_FOUND = 9999;
    public static final User user = new User(USER_ID, "User", "user@yandex.ru", "password", Role.USER);
    public static final User admin = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ADMIN);
    public static final User guest = new User(GUEST_ID, "Guest", "guest@yandex.ru", "guest");
    public static final User user2 = new User(GUEST_ID + 1, "User2", "user2@yandex.ru", "password2", Role.USER);
    public static final User user3 = new User(GUEST_ID + 2, "User3", "user3@yandex.ru", "password3", Role.USER);
    public static final User user4 = new User(GUEST_ID + 3, "User4", "user4@yandex.ru", "password4", Role.USER);
    public static final User user5 = new User(GUEST_ID + 4, "User5", "user5@yandex.ru", "password5", Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPas", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("Update");
        updated.setEmail("update@mail.ru");
        updated.setPassword("updatePas");
        updated.setEnabled(false);
        updated.setRoles(Collections.singleton(Role.ADMIN));
        return updated;
    }
}
