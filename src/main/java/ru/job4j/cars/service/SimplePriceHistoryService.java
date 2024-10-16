package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.PriceHistory;
import ru.job4j.cars.repository.PriceHistoryRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePriceHistoryService implements PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    @Override
    public PriceHistory save(PriceHistory priceHistory) {
        return priceHistoryRepository.save(priceHistory);
    }

    @Override
    public Optional<PriceHistory> findById(int id) {
        return priceHistoryRepository.findById(id);
    }

    @Override
    public List<PriceHistory> findAllOrderById() {
        return priceHistoryRepository.findAllOrderById();
    }

    @Override
    public List<PriceHistory> findByPostId(int postId) {
        return priceHistoryRepository.findByPostId(postId);
    }
}
