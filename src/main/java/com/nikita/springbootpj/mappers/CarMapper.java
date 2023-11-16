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
            mapper.getConfiguration().setAmbiguityIgnored(true);
            car = mapper.map(carDTO,Car.class);
        }
        return car;
    }

    public CarDTO fromCarToDTO(Car car){
        CarDTO carDTO = null;
        if(car != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            carDTO = mapper.map(car,CarDTO.class);
        }
        return carDTO;
    }


    public void updateCar(Car car, CarDTO carDTO){
        if(car != null && carDTO != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            mapper.map(carDTO,car);
        }
    }

}
