package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCarModelRepository implements CarModelRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmCarModelRepository.class);

    @Override
    public Optional<CarModel> findById(int id) {
        try {
            return crudRepository.optional("from CarModel WHERE id = :id", CarModel.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске модели с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<CarModel> findAllOrderById() {
        try {
            return crudRepository.query("from CarModel ORDER BY id", CarModel.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех моделей", e);
        }
        return List.of();
    }
}
