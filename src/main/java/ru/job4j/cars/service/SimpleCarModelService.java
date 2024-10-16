package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.CarModelRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarModelService implements CarModelService {

    private final CarModelRepository carModelRepository;

    @Override
    public Optional<CarModel> findById(int id) {
        return carModelRepository.findById(id);
    }

    @Override
    public List<CarModel> findAllOrderById() {
        return carModelRepository.findAllOrderById();
    }
}
