package ru.job4j.cars.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "car_brands")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarBrand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;
}