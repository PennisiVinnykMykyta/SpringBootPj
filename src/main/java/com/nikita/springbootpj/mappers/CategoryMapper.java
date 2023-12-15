package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.CategoryDTO;
import com.nikita.springbootpj.entities.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryMapper {
    private final ModelMapper modelMapper;

    public Category fromDtoToCategory(CategoryDTO categoryDTO){
        Category category = null;
        if(categoryDTO != null){
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            category = modelMapper.map(categoryDTO,Category.class);
        }
        return category;
    }

    public CategoryDTO fromCategoryToDTO(Category category){
        CategoryDTO categoryDTO = null;
        if(category != null){
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            categoryDTO = modelMapper.map(category,CategoryDTO.class);
        }
        return categoryDTO;
    }

    public void updateCategory(Category category, CategoryDTO categoryDTO){
        if(category != null && categoryDTO != null){
            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            modelMapper.map(categoryDTO,category);
        }
    }
}
