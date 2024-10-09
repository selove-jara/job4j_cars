package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmEngineRepository implements EngineRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmEngineRepository.class);

    @Override
    public Optional<Engine> findById(int id) {
        try {
            return crudRepository.optional("from Engine WHERE id = :id", Engine.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске двигателя с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Engine> findAllOrderById() {
        try {
            return crudRepository.query("from Engine ORDER BY id", Engine.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех двигателей", e);
        }
        return List.of();
    }
}
