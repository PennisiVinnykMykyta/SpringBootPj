package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.CarCategoryDTO;
import com.nikita.springbootpj.dto.CarCategoryToModifyDTO;

import java.util.List;

public interface CarCategoryService {
    void saveOrUpdateCarCategory(CarCategoryToModifyDTO carCategoryToModifyDTO);

    void deleteCarCategory(int id);

    List<CarCategoryDTO> getAllCarsCategories(int id);
    List<CarCategoryDTO> getAllCategorysCars(int id);

}
