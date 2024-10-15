package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    Owner save(Owner owner);

    Optional<Owner> findById(int id);

    List<Owner> findAllOrderById();

    Optional<Owner> findByUser(User user);
}
