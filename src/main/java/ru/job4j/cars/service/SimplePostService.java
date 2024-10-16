package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.PostRepository;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {

    private final PostRepository postRepository;
    private final PriceHistoryService priceHistoryService;
    private final FileService fileService;

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post createNewPost(User user, String description, LocalDateTime created,
                              int price, Car car, List<FileDto> filesDto, boolean sold) {

        var priceHistory = new PriceHistory(0, BigInteger.valueOf(0), BigInteger.valueOf(price), created);
        priceHistoryService.save(priceHistory);
        var files = filesDto.stream().map(fileService::save).toList();

        var post = new Post(0, description, created, user, List.of(priceHistory), List.of(user), car, files, false);
        return postRepository.save(post);
    }

    @Override
    public boolean update(Post post) {
        return postRepository.update(post);
    }

    @Override
    public boolean delete(int id) {
        return postRepository.delete(id);
    }

    @Override
    public Optional<Post> findById(int id) {
        return postRepository.findById(id);
    }

    @Override
    public List<Post> findAllOrderById() {
        return postRepository.findAllOrderById();
    }

    @Override
    public List<Post> findPostsForLastDay() {
        return postRepository.findPostsForLastDay();
    }

    @Override
    public List<Post> findPostsWithPhotos() {
        return postRepository.findPostsWithPhotos();
    }

    @Override
    public List<Post> findPostsByCarBrand(String brandName) {
        return postRepository.findPostsByCarBrand(brandName);
    }

    @Override
    public boolean complete(int id) {
        return postRepository.complete(id);
    }
}
