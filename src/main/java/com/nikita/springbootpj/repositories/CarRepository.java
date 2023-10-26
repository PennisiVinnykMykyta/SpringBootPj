package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer>, JpaSpecificationExecutor<Car> {

    List<Car> getCarsByBookingsIsNotContaining(Book book); //prende tutte i veicoli che non siano prenotati
}
