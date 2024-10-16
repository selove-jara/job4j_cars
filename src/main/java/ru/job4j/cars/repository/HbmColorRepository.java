package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Color;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmColorRepository implements ColorRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbmColorRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Optional<Color> findById(int id) {
        try {
            return crudRepository.optional("from Color WHERE id = :id", Color.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске цвета с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Color> findAllOrderById() {
        try {
            return crudRepository.query("from Color ORDER BY id", Color.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех цветов", e);
        }
        return List.of();
    }
}
