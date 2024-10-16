package ru.job4j.cars.repository;

import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository {
    PriceHistory save(PriceHistory priceHistory);

    Optional<PriceHistory> findById(int id);

    List<PriceHistory> findAllOrderById();

    List<PriceHistory> findByPostId(int postId);
}
