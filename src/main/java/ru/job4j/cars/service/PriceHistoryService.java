package ru.job4j.cars.service;

import ru.job4j.cars.model.PriceHistory;

import java.util.List;
import java.util.Optional;

public interface PriceHistoryService {
    PriceHistory save(PriceHistory priceHistory);

    Optional<PriceHistory> findById(int id);

    List<PriceHistory> findAllOrderById();

    List<PriceHistory> findByPostId(int postId);
}
