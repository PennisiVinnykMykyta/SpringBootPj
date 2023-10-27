package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.CarDTO;

import java.time.LocalDate;
import java.util.List;

public interface CarService {

    CarDTO getCarById(int id);

    List<CarDTO> getAllCars();

    List<CarDTO> availableCars(LocalDate start, LocalDate finish);

    void deleteCarById(int id);

    void saveOrUpdateCar(CarDTO carDTO);
}
