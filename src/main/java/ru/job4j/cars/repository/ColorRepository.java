package ru.job4j.cars.repository;

import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Optional;

public interface ColorRepository {
    Optional<Color> findById(int id);

    List<Color> findAllOrderById();
}
