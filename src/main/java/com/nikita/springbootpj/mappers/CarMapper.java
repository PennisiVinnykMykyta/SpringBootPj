package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.entities.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    /*public CarDTO fromCarToCarDTO(Car car) {
        String id = Integer.toString(car.getId());
        String numberPlate = car.getNumberPlate();
        String color = car.getColor();
        String model = car.getModel();
        String brand = car.getBrand();

        return new CarDTO(id, numberPlate, color, model, brand);

    }

    public Car fromCarDTOToCar(CarDTO carDTO) {
        Integer id = Integer.parseInt(carDTO.getId());
        String numberPlate = carDTO.getNumberPlate();
        String color = carDTO.getColor();
        String model = carDTO.getModel();
        String brand = carDTO.getBrand();

        return new Car(id, numberPlate, color, model, brand);
    }*/
}
