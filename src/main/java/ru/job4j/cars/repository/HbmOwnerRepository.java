package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@AllArgsConstructor
public class HbmOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmOwnerRepository.class);

    @Override
    public Owner save(Owner owner) {
        try {
            crudRepository.run(session -> session.merge(owner));
        } catch (Exception e) {
            LOGGER.error("Exception on save Owner", e);
        }
        return owner;
    }

    @Override
    public Optional<Owner> findById(int id) {
        try {
            return crudRepository.optional(
                    "FROM Owner o JOIN FETCH o.user WHERE o.id = :fId", Owner.class, Map.of("fId", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске владельца с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Owner> findAllOrderById() {
        try {
            return crudRepository.query("FROM Owner o JOIN FETCH o.user ORDER BY o.id", Owner.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех владельцов", e);
        }
        return List.of();
    }

    @Override
    public Optional<Owner> findByUser(User user) {
        try {
            return crudRepository.optional(
                    "FROM Owner o WHERE o.user.id = :userId",
                    Owner.class,
                    Map.of("userId", user.getId())
            );
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске владельца по пользователю", e);
        }
        return Optional.empty();
    }
}
