package ru.job4j.cars.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "car_body")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    private String name;
}
