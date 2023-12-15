package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    void saveOrUpdateCategory(CategoryDTO categoryDTO);

    void deleteById(int id);

    CategoryDTO getCategory(int id);

    List<CategoryDTO> getAllCategories();

}
