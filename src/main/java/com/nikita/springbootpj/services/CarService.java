package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.DownloadImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public interface CarService {

    CarDTO getCarById(int id);

    List<CarDTO> getAllCars();

    List<CarDTO> availableCars(LocalDate start, LocalDate finish);

    void deleteCarById(int id);

    void saveOrUpdateCar(CarDTO carDTO);

    void uploadCarPic(MultipartFile file, int carId) throws IOException;

    DownloadImageResponse downloadCarPic(int carId) throws IOException;
}
