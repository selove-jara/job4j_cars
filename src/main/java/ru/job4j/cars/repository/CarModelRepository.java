package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelRepository {
    Optional<CarModel> findById(int id);

    List<CarModel> findAllOrderById();
}
