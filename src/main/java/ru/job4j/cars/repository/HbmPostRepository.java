package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmPostRepository implements PostRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PostRepository.class);
    private final CrudRepository crudRepository;

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
}
