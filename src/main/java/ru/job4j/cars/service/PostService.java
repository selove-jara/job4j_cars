package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostService {
    Post save(Post post);

    Post createNewPost(User user, String description, LocalDateTime created, int price, Car car, List<FileDto> filesDto, boolean isActive);

    boolean update(Post post);

    boolean delete(int id);

    Optional<Post> findById(int id);

    List<Post> findAllOrderById();

    List<Post> findPostsForLastDay();

    List<Post> findPostsWithPhotos();

    List<Post> findPostsByCarBrand(String brandName);

    boolean complete(int id);
}
