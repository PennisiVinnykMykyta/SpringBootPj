package com.nikita.springbootpj.entities;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name ="car")
@Data
@Getter
@Setter
public class Car implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "car_id")
    private Integer id;

    @Column(name = "number_plate", nullable = false, unique = true)
    private String numberPlate;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "model", nullable = false)
    private String model;

    @Column(name = "brand", nullable = false)
    private String brand;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Book> bookings;

}
