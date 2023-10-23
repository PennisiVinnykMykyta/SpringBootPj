package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Car;

import java.util.List;

public interface CarRepository {

    void deleteById(int id);

    void saveOrUpdateCar(Car car);

    List<Car> findAll();

    Car findById(int id);

    List<Car> availableCars(List<Integer> BookedCars);

}
