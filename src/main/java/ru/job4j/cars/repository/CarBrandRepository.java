package ru.job4j.cars.repository;

import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Optional;

public interface CarBrandRepository {
    Optional<CarBrand> findById(int id);

    List<CarBrand> findAllOrderById();
}
