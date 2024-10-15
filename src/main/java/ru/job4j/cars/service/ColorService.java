package ru.job4j.cars.service;

import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Optional;

public interface ColorService {
    Optional<Color> findById(int id);

    List<Color> findAllOrderById();
}
