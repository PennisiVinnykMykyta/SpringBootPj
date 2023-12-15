package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.CarCategoryDTO;
import com.nikita.springbootpj.dto.CarCategoryToModifyDTO;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.CarCategory;
import com.nikita.springbootpj.entities.Category;
import com.nikita.springbootpj.mappers.CarCategoryMapper;
import com.nikita.springbootpj.mappers.CarMapper;
import com.nikita.springbootpj.mappers.CategoryMapper;
import com.nikita.springbootpj.repositories.CarCategoryRepository;
import com.nikita.springbootpj.repositories.CarRepository;
import com.nikita.springbootpj.repositories.CategoryRepository;
import com.nikita.springbootpj.services.CarCategoryService;
import com.nikita.springbootpj.services.CarService;
import com.nikita.springbootpj.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CarCategoryServiceImplementation implements CarCategoryService {

    private final CarCategoryRepository carCategoryRepository;
    private final CarRepository carRepository;
    private final CategoryRepository categoryRepository;
    private final CarCategoryMapper carCategoryMapper;
    private final CategoryMapper categoryMapper;
    private final CarMapper carMapper;
    private final CategoryService categoryService;
    private final CarService carService;

    public void saveOrUpdateCarCategory(CarCategoryToModifyDTO carCategoryToModifyDTO){
        Car car = carRepository.findById(carCategoryToModifyDTO.getCarId()).orElseThrow(() -> new RuntimeException("Car Not Found"));
        Category category = categoryRepository.findById(carCategoryToModifyDTO.getCategoryId()).orElseThrow(() -> new RuntimeException("Category Not Found"));

        if(carCategoryToModifyDTO.getCarCategoryId() == null){
            carCategoryRepository.save(carCategoryMapper.fromDTOtoCarCategory(category,car,carCategoryToModifyDTO));
        }else{
            Optional<CarCategory> optionalCarCategory = carCategoryRepository.findById(carCategoryToModifyDTO.getCarCategoryId());
            if(optionalCarCategory.isPresent()){
                CarCategory carCategory = optionalCarCategory.get();
                carCategoryMapper.updateCarCategory(carCategory,carCategoryToModifyDTO,car,category);
                carCategoryRepository.save(carCategory);
            }
        }
    }

    public void deleteCarCategory(int id){
        carCategoryRepository.findById(id).orElseThrow(() -> new RuntimeException("CarCategory Not Found"));
        carCategoryRepository.deleteById(id);
    }

    public List<CarCategoryDTO> getAllCategorysCars(int id){
        Category category = categoryMapper.fromDtoToCategory(categoryService.getCategory(id));
        List<CarCategoryDTO> categoryDTOList = new ArrayList<>();

        if(carCategoryRepository.getCarCategoriesByCategory(category) != null){
            for(CarCategory carCategory : carCategoryRepository.getCarCategoriesByCategory(category)){
                categoryDTOList.add(carCategoryMapper.fromCarCategoryToDTO(carCategory));
            }
        }
        return categoryDTOList;
    }
    public List<CarCategoryDTO> getAllCarsCategories(int id){
        Car car = carMapper.fromDtoToCar(carService.getCarById(id));
        List<CarCategoryDTO> categoryDTOList = new ArrayList<>();

        if(carCategoryRepository.getCarCategoriesByCar(car) != null){
            for(CarCategory carCategory : carCategoryRepository.getCarCategoriesByCar(car)){
                categoryDTOList.add(carCategoryMapper.fromCarCategoryToDTO(carCategory));
            }
        }
        return categoryDTOList;
    }
}
