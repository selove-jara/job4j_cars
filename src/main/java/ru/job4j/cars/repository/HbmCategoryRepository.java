package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Category;
import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(HbmCategoryRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Optional<Category> findById(int id) {
        try {
            return crudRepository.optional("from Category WHERE id = :id", Category.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске категории с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<Category> findAllOrderById() {
        try {
            return crudRepository.query("from Category ORDER BY id", Category.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех категорий", e);
        }
        return List.of();
    }
}
