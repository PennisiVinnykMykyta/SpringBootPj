package com.nikita.springbootpj.controllers;

import com.nikita.springbootpj.dto.CarCategoryDTO;
import com.nikita.springbootpj.dto.CarCategoryToModifyDTO;
import com.nikita.springbootpj.services.CarCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/car-category")

public class CarCategoryController {
    private  final CarCategoryService categoryService;

    @DeleteMapping("/delete/{id}")
    public void deleteCarCategory(@PathVariable("id") int id){
        categoryService.deleteCarCategory(id);
    }

    @RequestMapping(value = "/add-or-update", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateBook(@RequestBody CarCategoryToModifyDTO carCategoryToModifyDTO){
        categoryService.saveOrUpdateCarCategory(carCategoryToModifyDTO);
    }

    @GetMapping("/list/by-car/{carId}")
    public ResponseEntity<CarCategoryDTO> getCarsCarCategories(@PathVariable("carId") int id){
        List<CarCategoryDTO> categoryDTOList = categoryService.getCarsCategories(id);
        return new ResponseEntity(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/list/by-category/{categoryId}")
    public ResponseEntity<CarCategoryDTO> getCategorysCars(@PathVariable("categoryId") int id){
        List<CarCategoryDTO> categoryDTOList = categoryService.getCategorysCars(id);
        return new ResponseEntity(categoryDTOList, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<CarCategoryDTO> getAllCarCategories(){
        List<CarCategoryDTO> categoryDTOList = categoryService.getAllCarCategories();
        return new ResponseEntity(categoryDTOList, HttpStatus.OK);
    }

}
