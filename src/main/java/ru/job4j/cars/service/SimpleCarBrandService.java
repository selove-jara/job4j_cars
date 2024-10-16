package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.CarBrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarBrandService implements CarBrandService {

    private final CarBrandRepository carBrandRepository;

    @Override
    public Optional<CarBrand> findById(int id) {
        return carBrandRepository.findById(id);
    }

    @Override
    public List<CarBrand> findAllOrderById() {
        return carBrandRepository.findAllOrderById();
    }
}
