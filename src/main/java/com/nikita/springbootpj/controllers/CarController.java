package com.nikita.springbootpj.controllers;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.DownloadImageResponse;
import com.nikita.springbootpj.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car")
public class CarController {

    private final CarService carService;

    @PostMapping("/car-pic/upload/{carId}")
    public void uploadImage(@RequestPart("imageFile") MultipartFile file, @PathVariable int carId) throws IOException {
        carService.uploadCarPic(file,carId);
    }

    @GetMapping(value = "/car-pic/download/{carId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DownloadImageResponse> downloadImage(@PathVariable int carId) throws IOException{

        DownloadImageResponse downloadImageResponse = carService.downloadCarPic(carId);
        return new ResponseEntity<>(downloadImageResponse,HttpStatus.OK);
    }

    @GetMapping("/get/{carId}")
    public ResponseEntity<CarDTO> getCar(@PathVariable("carId") int id){
        CarDTO carDTO = carService.getCarById(id);
        if(carDTO != null){
            return  new ResponseEntity<>(carDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/available-cars/{start},{finish}")
    public ResponseEntity<CarDTO> availableCars(
            @PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @PathVariable("finish") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finish
            )
    {
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
    public void addOrUpdateCar(@RequestBody CarDTO carDTO){
        carService.saveOrUpdateCar(carDTO);
    }

    @DeleteMapping("/delete/{carId}")
    public void deleteCar(@PathVariable("carId") int id){
        carService.deleteCarById(id);
    }
}
