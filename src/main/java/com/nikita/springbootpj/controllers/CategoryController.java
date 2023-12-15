package com.nikita.springbootpj.controllers;

import com.nikita.springbootpj.dto.CategoryDTO;
import com.nikita.springbootpj.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping(value = "/add-or-update", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateCategory(@RequestBody CategoryDTO categoryDTO){categoryService.saveOrUpdateCategory(categoryDTO);}

    @DeleteMapping("/delete/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") int categoryId){categoryService.deleteById(categoryId);}

    @GetMapping("/list")
    public ResponseEntity<CategoryDTO> getAllCategories(){
        List<CategoryDTO> categoryDTOList = categoryService.getAllCategories();
        if(categoryDTOList != null){
            return new ResponseEntity(categoryDTOList, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategory(@PathVariable("categoryId") int categoryId){
        CategoryDTO categoryDTO = categoryService.getCategory(categoryId);
        if(categoryDTO != null){
            return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
