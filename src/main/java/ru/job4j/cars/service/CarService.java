package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface CarService {
    Car save(Car car);

    Car createCar(User user, String carName, int engineId, int carModelId, int carBrandId, int carBodyId, int colorId, int categoryId);

    boolean update(Car car);

    boolean delete(int id);

    Optional<Car> findById(int id);

    List<Car> findAllOrderById();
}
