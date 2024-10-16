package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {
    Owner save(Owner owner);

    Optional<Owner> findById(int id);

    List<Owner> findAllOrderById();

    Optional<Owner> findByUser(User user);
}
