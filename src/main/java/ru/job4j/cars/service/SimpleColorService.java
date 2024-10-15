package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Color;
import ru.job4j.cars.repository.ColorRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleColorService implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    public Optional<Color> findById(int id) {
        return colorRepository.findById(id);
    }

    @Override
    public List<Color> findAllOrderById() {
        return colorRepository.findAllOrderById();
    }
}
