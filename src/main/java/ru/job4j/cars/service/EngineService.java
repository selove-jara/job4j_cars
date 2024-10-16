package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineService {
    Optional<Engine> findById(int id);

    List<Engine> findAllOrderById();
}
