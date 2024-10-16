package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.CarBodyRepository;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.OwnerRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SimpleCarService implements CarService {

    private final CarRepository carRepository;
    private final EngineService engineService;
    private final CarBrandService carBrandService;
    private final CarModelService carModelService;
    private final CarBodyService carBodyService;
    private final OwnerService ownerService;
    private final ColorService colorService;
    private final CategoryService categoryService;

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car createCar(User user, String carName, int engineId, int carBrandId, int carModelId, int carBodyId, int colorId, int categoryId) {
        var engine = engineService.findById(engineId).orElseThrow(() ->
                new IllegalArgumentException("Двигатель не найден"));
        var carModel = carModelService.findById(carModelId).orElseThrow(() ->
                new IllegalArgumentException("Модель авто не найдена"));
        var carBrand = carBrandService.findById(carBrandId).orElseThrow(() ->
                new IllegalArgumentException("Марка авто не найдена"));
        var carBody = carBodyService.findById(carBodyId).orElseThrow(() ->
                new IllegalArgumentException("Кузов авто не найден"));
        var color = colorService.findById(colorId).orElseThrow(() ->
                new IllegalArgumentException("Цвет не найден"));
        var category = categoryService.findById(categoryId).orElseThrow(() ->
                new IllegalArgumentException("Категория не найдена"));

        Owner owner = new Owner(0, user.getFullName(), user);
        ownerService.save(owner);

        var car = new Car(0, carName, engine, carBrand, carModel, Set.of(owner), carBody, color, category);
        return save(car);
    }

    @Override
    public boolean update(Car car) {
        return carRepository.update(car);
    }

    @Override
    public boolean delete(int id) {
        return carRepository.delete(id);
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public List<Car> findAllOrderById() {
        return carRepository.findAllOrderById();
    }
}
