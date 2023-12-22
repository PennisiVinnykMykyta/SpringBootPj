package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.DownloadImageResponse;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.CarCategory;
import com.nikita.springbootpj.entities.Category;
import com.nikita.springbootpj.mappers.CarMapper;
import com.nikita.springbootpj.repositories.CarCategoryRepository;
import com.nikita.springbootpj.repositories.CarRepository;
import com.nikita.springbootpj.repositories.CategoryRepository;
import com.nikita.springbootpj.services.BookService;
import com.nikita.springbootpj.services.CarService;
import lombok.RequiredArgsConstructor;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarServiceImplementation implements CarService {

    private final CarMapper carMapper;
    private final CarRepository carRepository;
    private final BookService bookService;

    private final CarCategoryRepository carCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Value("${profile-folder.path}")
    private String folderPath;

    public void uploadCarPic(MultipartFile file, int carId) throws IOException {

        Optional<Car> optionalCar = carRepository.findById(carId);

        if(optionalCar.isPresent()){
            Car car = optionalCar.get();

            if(car.getCarPicName() != null){
                File currentImage = new File(folderPath+car.getCarPicName());
                if(!currentImage.delete()){
                    throw new IOException("Couldn't delete Car Pic");
                }
            }

             car.setCarPicName(carId+file.getOriginalFilename());
            carRepository.save(car);

            file.transferTo(new File(folderPath+carId+file.getOriginalFilename()));
        } else{
            throw new IOException("couldn't transfer the file to the folder");
        }


    }

    public DownloadImageResponse downloadCarPic(int carId) throws IOException {
        Optional<Car> optionalCar = carRepository.findById(carId);
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            String path = car.getCarPicName();
            DownloadImageResponse downloadImageResponse = new DownloadImageResponse();
            byte[] fileContent;
            String imageType;

            if (path != null) {

                imageType = path.substring(path.lastIndexOf('.')+1);

                fileContent = FileUtil.readAsByteArray(new File(folderPath+path));

            }else{
                imageType = "jpg";

                fileContent = FileUtil.readAsByteArray(new File(folderPath+"basicUser.jpg"));
            }

            String encodedImage = Base64.getEncoder().encodeToString(fileContent);

            downloadImageResponse.setImage(encodedImage);
            downloadImageResponse.setContentType(imageType);
            return downloadImageResponse;
        }else{
            throw new IOException ("CarNotFound!");
        }

    }

    public List<CarDTO> getCarsOfCategory(String attribute){
        Category category = categoryRepository.getCategoryByAttribute(attribute);
        if(category != null){

            List<CarCategory> carCategoryList = carCategoryRepository.getCarCategoriesByCategory(category);

            List<CarDTO> carDTOList = new ArrayList<>();
            for(CarCategory carCategory : carCategoryList){
                carDTOList.add(carMapper.fromCarToDTO(carCategory.getCar()));
            }
            return carDTOList;
        }else{
            return null;
        }
    }

    public CarDTO getCarById(int id){
        CarDTO carDTO = null;
        if(carRepository.findById(id).isPresent()){
            carDTO = carMapper.fromCarToDTO(carRepository.findById(id).get());
        }

        try {
            DownloadImageResponse image = this.downloadCarPic(carDTO.getId());
            carDTO.setImage(image.getImage());
            carDTO.setImageType(image.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return carDTO;

    }

    public List<CarDTO> getAllCars(){
        List<Car> carList = carRepository.findAll();
        List<CarDTO> cars = new ArrayList<>();

        for(Car car: carList){
            cars.add(carMapper.fromCarToDTO(car));
        }

        for(CarDTO carDTO: cars){
            try {
                DownloadImageResponse image = this.downloadCarPic(carDTO.getId());
                carDTO.setImage(image.getImage());
                carDTO.setImageType(image.getContentType());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return cars;
    }

    public List<CarDTO> availableCars(LocalDate start, LocalDate finish){
        List<CarDTO> carList = new ArrayList<>();

        if (start.isAfter(LocalDate.now().plusDays(2)) && !start.isAfter(finish)) {
            carList = this.getAllCars();
            List<CarDTO> bookedCars = bookService.bookedCars(start, finish);

            if (bookedCars != null) {
                for (CarDTO car : bookedCars) {
                    carList.remove(car);
                }
            }
            //se la lista delle macchine prenotate e' vuota allora ritrona tutte le macchine
        }
        return carList;

    }


    public void deleteCarById(int id){
        CarDTO carDTO = this.getCarById(id);
        if(carDTO != null){
            carRepository.deleteById(carDTO.getId());
        }
    }

    public void saveOrUpdateCar(CarDTO carDTO){
        if(carDTO.getId() == null){ // if there is no id we save him
            carRepository.save(carMapper.fromDtoToCar(carDTO));
        }else{
            Optional<Car> optionalCar = carRepository.findById(carDTO.getId());
            if(optionalCar.isPresent()){
                Car car = optionalCar.get();
                carMapper.updateCar(car,carDTO);
                carRepository.save(car);
            }
        }
    }
}
