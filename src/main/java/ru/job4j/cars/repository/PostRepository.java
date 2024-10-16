package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);

    boolean update(Post post);

    boolean delete(int id);

    Optional<Post> findById(int id);

    List<Post> findAllOrderById();

    List<Post> findPostsForLastDay();

    List<Post> findPostsWithPhotos();

    List<Post> findPostsByCarBrand(String brandName);

    boolean complete(int id);
}
