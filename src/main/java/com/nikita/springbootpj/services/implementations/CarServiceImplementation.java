package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.mappers.CarMapper;
import com.nikita.springbootpj.repositories.CarRepository;
import com.nikita.springbootpj.services.BookService;
import lombok.RequiredArgsConstructor;
import org.hibernate.sql.exec.ExecutionException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImplementation {

    private final CarMapper carMapper;
    private final CarRepository carRepository;
    private final BookService bookService;
    public CarDTO getCarById(int id){
        CarDTO carDTO = null;
        if(carRepository.findById(id).isPresent()){
            carDTO = carMapper.fromCarToDTO(carRepository.findById(id).get());
        }
        return carDTO;

    }

    List<CarDTO> getAllCars(){
        List<Car> carList = carRepository.findAll();
        List<CarDTO> cars = new ArrayList<>();

        for(Car car: carList){
            cars.add(carMapper.fromCarToDTO(car));
        }
        return cars;
    }

    List<CarDTO> availableCars(LocalDate start, LocalDate finish){

        if(start.isAfter(LocalDate.now().minusDays(2)) || start.isAfter(finish)){
            return null;

        }else{
            List<CarDTO> carList = this.getAllCars();
            List<CarDTO> bookedCars = bookService.bookedCars(start,finish);

            if(bookedCars != null || !bookedCars.isEmpty()){
                for(CarDTO car : bookedCars){
                    carList.remove(car);
                }
            }
            //se la lista delle macchine prenotate e' vuota allora ritrona tutte le macchine
            return carList;
        }

    }


    void deleteCarById(int id){
        CarDTO carDTO = this.getCarById(id);
        if(carDTO != null){
            carRepository.deleteById(carDTO.getId());
        }
    }

    void saveOrUpdateCar(CarDTO carDTO){
        if(carDTO.getId() == null){ // if there is no id we save him
            carRepository.save(carMapper.fromDtoToCar(carDTO));
        }else if(carRepository.findById(carDTO.getId()).isPresent()){ // else we need to take the updated version and save it
            CarDTO carToModify = carMapper.fromDTOToModify(carDTO);
            carRepository.save(carMapper.fromDtoToCar(carToModify));
        }
    }
}
