package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmPriceHistoryRepository implements PriceHistoryRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmPriceHistoryRepository.class);

    @Override
    public PriceHistory save(PriceHistory priceHistory) {
        try {
            crudRepository.run(session -> session.merge(priceHistory));
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранение", e);
        }
        return priceHistory;
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        try {
            return crudRepository.optional("from PriceHistory WHERE id = :id", PriceHistory.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске цены с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<PriceHistory> findAllOrderById() {
        try {
            return crudRepository.query("from PriceHistory ORDER BY id", PriceHistory.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех цен", e);
        }
        return List.of();
    }

    @Override
    public List<PriceHistory> findByPostId(int postId) {
        try {
            return crudRepository.query("from PriceHistory WHERE id = :id", PriceHistory.class, Map.of("id", postId));
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении истории цен для поста с id: {}", postId, e);
        }
        return List.of();
    }
}
