package ru.job4j.cars.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private String name;

    private byte[] content;
}
