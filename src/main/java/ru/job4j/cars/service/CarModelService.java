package ru.job4j.cars.service;

import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelService {
    Optional<CarModel> findById(int id);

    List<CarModel> findAllOrderById();
}
