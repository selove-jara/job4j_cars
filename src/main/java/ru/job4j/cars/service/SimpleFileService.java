package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.repository.FileRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SimpleFileService implements FileService {

    private final FileRepository fileRepository;
    private final String storageDirectory;

    public SimpleFileService(FileRepository fileRepository, @Value("${file.directory}") String storageDirectory) {
        this.fileRepository = fileRepository;
        this.storageDirectory = storageDirectory;
    }

    @Override
    public File save(FileDto fileDto) {
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileDto.getName();
        String filePath = storageDirectory + "/" + uniqueFileName;
        writeFileBytes(filePath, fileDto.getContent());
        File file = new File();
        file.setName(uniqueFileName);
        file.setPath(filePath);
        return fileRepository.save(file);
    }

    @Override
    public Optional<FileDto> findById(int id) {
        var file = fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Файл с id " + id + " не найден"));
        var content = readFileAsBytes(file.getPath());
        return Optional.of(new FileDto(file.getName(), content));
    }

    @Override
    public List<FileDto> findAllOrderById() {
        var files = fileRepository.findAllOrderById();
        return files.stream()
                .map(file -> {
                    var content = readFileAsBytes(file.getPath());
                    return new FileDto(file.getName(), content);
                })
                .collect(Collectors.toList());
    }

    private void writeFileBytes(String path, byte[] content) {
        try {
            Files.write(Path.of(path), content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] readFileAsBytes(String path) {
        try {
            return Files.readAllBytes(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
