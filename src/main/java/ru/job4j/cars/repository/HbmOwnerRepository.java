package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmOwnerRepository implements OwnerRepository {
    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmOwnerRepository.class);

    @Override
    public Optional<Owner> findById(int id) {
        try {
            return crudRepository.optional("from Owner WHERE id = :id", Owner.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске владельца с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Owner> findAllOrderById() {
        try {
            return crudRepository.query("from Owner ORDER BY id", Owner.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех владельцов", e);
        }
        return List.of();
    }
}
