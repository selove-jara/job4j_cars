package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class);
    private final CrudRepository crudRepository;

    @Override
    public Post save(Post post) {
        try {
            crudRepository.run(session -> session.merge(post));
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранение", e);
        }
        return post;
    }

    @Override
    public boolean update(Post post) {
        try {
            return crudRepository.run("""
                                UPDATE Post SET 
                                    description = :fDescription, 
                                    created = :fCreated, 
                                    car_id = :fCarId,
                                    auto_user_id = :fUserId
                                WHERE id = :fId
                            """,
                    Map.of(
                            "fDescription", post.getDescription(),
                            "fCreated", post.getCreated(),
                            "fCarId", post.getCar().getId(),
                            "fUserId", post.getUser().getId(),
                            "fId", post.getId()
                    ));
        } catch (Exception e) {
            LOGGER.error("Exception on update Post", e);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        try {
            return crudRepository.run(
                    "delete from Post where id = :fId",
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            LOGGER.error("Ошибка при удалении объявления с id: {}", id, e);
            return false;
        }
    }

    public Optional<Post> findById(int id) {
        try {
            return crudRepository.optional(
                    "select p from Post p join fetch p.files where p.id = :id",
                    Post.class,
                    Map.of("id", id)
            );
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске объявление по ID", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Post> findAllOrderById() {
        try {
            return crudRepository.query("select p from Post p join fetch p.files ORDER BY p.id", Post.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех постов", e);
            return List.of();
        }
    }

    public List<Post> findPostsForLastDay() {
        try {
            return crudRepository.query("from Post p where p.created >= :yesterday", Post.class, Map.of("yesterday", LocalDateTime.now().minusDays(1)));
        } catch (Exception e) {
            LOGGER.error("Ошибка при выборе объявлений за последний день", e);
        }
        return List.of();
    }

    public List<Post> findPostsWithPhotos() {
        try {
            return crudRepository.query("select distinct p from Post p join p.files f where f.id is not null", Post.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при выборе объявлений с фотографиями", e);
        }
        return List.of();
    }

    public List<Post> findPostsByCarBrand(String brandName) {
        try {
            return crudRepository.query("select p from Post p where p.car.carBrand.name = :brandName", Post.class, Map.of("brandName", brandName));
        } catch (Exception e) {
            LOGGER.error("Ошибка при выборе объявлений по марке автомобиля : {}", brandName, e);
        }
        return List.of();
    }

    @Override
    public boolean complete(int id) {
        try {
            return crudRepository.run("UPDATE Post SET isactive = true WHERE id = :fId", Map.of("fId", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при завершении задачи с id: {}", id, e);
            return false;
        }
    }
}