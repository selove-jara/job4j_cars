package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBody;
import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCarBodyRepository implements  CarBodyRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmCarBodyRepository.class);

    @Override
    public Optional<CarBody> findById(int id) {
        try {
            return crudRepository.optional("from CarBody WHERE id = :id", CarBody.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске кузова с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<CarBody> findAllOrderById() {
        try {
            return crudRepository.query("from CarBody ORDER BY id", CarBody.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех кузовов ", e);
        }
        return List.of();
    }
}

