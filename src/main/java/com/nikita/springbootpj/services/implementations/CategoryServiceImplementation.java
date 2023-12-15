package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.CategoryDTO;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.Category;
import com.nikita.springbootpj.mappers.CategoryMapper;
import com.nikita.springbootpj.repositories.CategoryRepository;
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
public class CategoryServiceImplementation implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public void saveOrUpdateCategory(CategoryDTO categoryDTO){

        if(categoryDTO.getId() == null){
            categoryRepository.save(categoryMapper.fromDtoToCategory(categoryDTO));
        }else{
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getId());
            if(optionalCategory.isPresent()){
                Category category = optionalCategory.get();
                categoryMapper.updateCategory(category,categoryDTO);
                categoryRepository.save(category);
            }
        }
    }

    public void deleteById(int categoryId){

        if(categoryRepository.findById(categoryId).isPresent()){
            categoryRepository.deleteById(categoryId);
        }
    }

    public CategoryDTO getCategory(int id){

        return  categoryMapper.fromCategoryToDTO(categoryRepository.findById(id).orElseThrow(() ->new RuntimeException("characteristic not found")));

    }

    public List<CategoryDTO> getAllCategories(){
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        for(Category category: categories){
            categoryDTOList.add(categoryMapper.fromCategoryToDTO(category));
        }
        return categoryDTOList;
    }
}
