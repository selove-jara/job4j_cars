package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.User;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmUserRepository implements UserRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmUserRepository.class);

    @Override
    public Optional<User> save(User user) {
        try {
            crudRepository.run(session -> session.save(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранении пользователя: {}", user, e);
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findByEmailAndPassword(String login, String password) {
        try {
            return crudRepository.optional(
                    "FROM User WHERE login = :fLogin AND password = :fPassword",
                    User.class,
                    Map.of("fLogin", login, "fPassword", password)
            );
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске пользователя по login и паролю. Login: {}", login, e);
            return Optional.empty();
        }
    }
}