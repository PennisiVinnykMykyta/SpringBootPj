package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    void saveOrUpdateCategory(CategoryDTO categoryDTO);

    void deleteByLabel(String label);

    CategoryDTO getCategory(int id);

    List<CategoryDTO> getAllCategories();

    List<CategoryDTO> getCategoriesOfCar(int id);

    void updateAttribute(CategoryDTO categoryDTO, int carId);

}
