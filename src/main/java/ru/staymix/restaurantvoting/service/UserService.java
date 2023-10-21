package ru.staymix.restaurantvoting.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.staymix.restaurantvoting.model.User;
import ru.staymix.restaurantvoting.repository.UserRepository;

import java.util.List;

import static org.springframework.util.Assert.notNull;

@Service
@AllArgsConstructor
public class UserService {
    private static final Sort SORT_NAME_EMAIL = Sort.by(Sort.Direction.ASC, "name", "email");

    private final UserRepository repository;

    public User create(User user) {
        notNull(user, "user must not be null");
        return repository.prepareAndSave(user);
    }

    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public User get(int id) {
        return repository.getExisted(id);
    }

    public User getByEmail(String email) {
        notNull(email, "email must not be null");
        return repository.getExistedByEmail(email);
    }

    public List<User> getAll() {
        return repository.findAll(SORT_NAME_EMAIL);
    }

    public void update(User user) {
        notNull(user, "user must not be null");
        repository.prepareAndSave(user);
    }
}
