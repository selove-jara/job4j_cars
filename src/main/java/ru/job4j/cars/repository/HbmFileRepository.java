package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbmFileRepository implements FileRepository {

    private final CrudRepository crudRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(HbmFileRepository.class);

    @Override
    public File save(File file) {
        try {
            crudRepository.run(session -> session.persist(file));
        } catch (Exception e) {
            LOGGER.error("Ошибка при сохранение файла", e);
        }
        return file;
    }

    @Override
    public Optional<File> findById(int id) {
        try {
            return crudRepository.optional("from File WHERE id = :id", File.class, Map.of("id", id));
        } catch (Exception e) {
            LOGGER.error("Ошибка при поиске файла с id: {}", id, e);
        }
        return Optional.empty();
    }

    @Override
    public List<File> findAllOrderById() {
        try {
            return crudRepository.query("from File ORDER BY id", File.class);
        } catch (Exception e) {
            LOGGER.error("Ошибка при получении всех файлов", e);
        }
        return List.of();
    }
}
