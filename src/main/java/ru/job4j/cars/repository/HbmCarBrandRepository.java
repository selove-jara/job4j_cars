package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCarBrandRepository implements CarBrandRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmCarBrandRepository.class);

    @Override
    public Optional<CarBrand> findById(int id) {
        try {
            return crudRepository.optional("from CarBrand c WHERE c.id = :id", CarBrand.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске марки с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<CarBrand> findAllOrderById() {
        try {
            return crudRepository.query("from CarBrand ORDER BY id", CarBrand.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех марок ", e);
        }
        return List.of();
    }
}
