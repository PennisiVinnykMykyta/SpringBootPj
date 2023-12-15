package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer>{

}
