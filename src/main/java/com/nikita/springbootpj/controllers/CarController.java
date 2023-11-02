package com.nikita.springbootpj.controllers;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @GetMapping("/get/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable("carId") int id){
        CarDTO carDTO = carService.getCarById(id);
        if(carDTO != null){
            return  new ResponseEntity<>(carDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/available-cars")
    public ResponseEntity<CarDTO> availableCars(@RequestBody LocalDate start, LocalDate finish){
        List<CarDTO> carsList = carService.availableCars(start,finish);
        if(carsList != null){
            return new ResponseEntity(carsList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<CarDTO> getAllCars(){
        List<CarDTO> carsList = carService.getAllCars();
        if(carsList != null){
            return new ResponseEntity(carsList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add-or-update", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateCar(@RequestBody CarDTO carDTO) throws ParseException {
        carService.saveOrUpdateCar(carDTO);
    }

    @DeleteMapping("/delete/{carId}")
    public void deleteCar(@PathVariable("carId") int id){
        carService.deleteCarById(id);
    }
}
