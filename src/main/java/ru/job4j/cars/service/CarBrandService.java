package ru.job4j.cars.service;

import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Optional;

public interface CarBrandService {
    Optional<CarBrand> findById(int id);

    List<CarBrand> findAllOrderById();

}
