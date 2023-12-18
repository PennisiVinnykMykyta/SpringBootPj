package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.CarCategory;
import com.nikita.springbootpj.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarCategoryRepository extends JpaRepository<CarCategory,Integer> {

    List<CarCategory> getCarCategoriesByCar(Car car);
    List<CarCategory> getCarCategoriesByCategory(Category category);

    CarCategory getCarCategoryByCarAndCategory(Car car, Category category);
}
