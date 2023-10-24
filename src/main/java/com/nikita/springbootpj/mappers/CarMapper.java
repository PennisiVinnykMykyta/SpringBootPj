package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.entities.Car;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarMapper {

    private final ModelMapper mapper;

    public Car fromDtoToCar(CarDTO carDTO){
        Car car = null;
        if(carDTO != null){
            car = mapper.map(carDTO,Car.class);
        }
        return car;
    }

    public CarDTO fromCarToDTO(Car car){
        CarDTO carDTO = null;
        if(car != null){
            carDTO = mapper.map(car,CarDTO.class);
        }
        return carDTO;
    }

}
