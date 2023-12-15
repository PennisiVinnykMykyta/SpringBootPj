package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.CarCategoryDTO;
import com.nikita.springbootpj.dto.CarCategoryToModifyDTO;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.CarCategory;
import com.nikita.springbootpj.entities.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CarCategoryMapper {

    private final ModelMapper mapper;
    private final CarMapper carMapper;
    private final CategoryMapper categoryMapper;

    public CarCategory fromDTOtoCarCategory(Category category, Car car, CarCategoryToModifyDTO carCategoryToModifyDTO){
        CarCategory carCategory = null;
        if(carCategoryToModifyDTO != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            carCategory = mapper.map(carCategoryToModifyDTO,CarCategory.class);
            carCategory.setCar(car);
            carCategory.setCategory(category);
        }
        return carCategory;
    }

    public CarCategoryDTO fromCarCategoryToDTO(CarCategory carCategory){
        CarCategoryDTO categoryDTO = null;
        if(carCategory != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            categoryDTO = mapper.map(carCategory,CarCategoryDTO.class);
            categoryDTO.setCar(carMapper.fromCarToDTO(carCategory.getCar()));
            categoryDTO.setCategory(categoryMapper.fromCategoryToDTO(carCategory.getCategory()));
        }
        return categoryDTO;
    }

    public void updateCarCategory(CarCategory carCategory,CarCategoryToModifyDTO categoryToModifyDTO,Car car,Category category) {
        if(carCategory != null && categoryToModifyDTO != null){
            carCategory.setCategory(category);
            carCategory.setCar(car);
        }

    }

}
