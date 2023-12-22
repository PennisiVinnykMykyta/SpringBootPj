package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.CategoryDTO;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.CarCategory;
import com.nikita.springbootpj.entities.Category;
import com.nikita.springbootpj.mappers.CarMapper;
import com.nikita.springbootpj.mappers.CategoryMapper;
import com.nikita.springbootpj.repositories.CarCategoryRepository;
import com.nikita.springbootpj.repositories.CarRepository;
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

    private final CarRepository carRepository;
    private final CarCategoryRepository carCategoryRepository;

    private final CarMapper carMapper;

    public void saveOrUpdateCategory(CategoryDTO categoryDTO){

        if(categoryDTO.getId() == null){
            categoryRepository.save(categoryMapper.fromDtoToCategory(categoryDTO));

            //se è una nuova categoria mettiamo associamo la aggiungiamo a tutte le macchine con un valore di default

            Category category = categoryRepository.getCategoryByAttributeAndLabel(categoryDTO.getAttribute(),categoryDTO.getLabel());
            List<Car> cars = carRepository.findAll();
            for(Car car : cars){
                CarCategory carCategory = new CarCategory();
                carCategory.setCar(car);
                carCategory.setCategory(category);
                carCategoryRepository.save(carCategory);
            }


        }else{
            Optional<Category> optionalCategory = categoryRepository.findById(categoryDTO.getId());
            if(optionalCategory.isPresent()){
                Category category = optionalCategory.get();
                categoryMapper.updateCategory(category,categoryDTO);
                categoryRepository.save(category);
            }
        }
    }

    public void updateAttribute(CategoryDTO categoryDTO, int carId){

        Optional<Car> carOptional = carRepository.findById(carId);
        Optional<Category> optional = categoryRepository.findCategoryByAttributeAndLabel(categoryDTO.getAttribute(),categoryDTO.getLabel());

        //prima vediamo se la macchina esiste
        if(carOptional.isPresent()){
            Car car = carOptional.get();
            Category category;

            //adesso vediamo se esiste una categoria con tale attributo unico oppure
            //se ne dobbiamo creare una nuova con lo stesso label
            if(optional.isEmpty()){
                //save new category
                category = new Category();
                category.setAttribute(categoryDTO.getAttribute());
                category.setLabel(categoryDTO.getLabel());
                categoryRepository.save(category);

                category = categoryRepository.getCategoryByAttributeAndLabel(categoryDTO.getAttribute(),categoryDTO.getLabel());
            }else{
                category=optional.get();
            }

            //aggiorniamo la carCategory Esistente
            List<CarCategory> carCategoryList = carCategoryRepository.getCarCategoriesByCar(car);
            for(CarCategory carCategory : carCategoryList){
                if(carCategory.getCategory().getLabel().equals(categoryDTO.getLabel())){
                    carCategory.setCategory(category);
                    carCategory.setCar(car);
                    carCategoryRepository.save(carCategory);
                }
            }
        }
    }

    public List<CategoryDTO> getCategoriesOfCar(int carId){
        Optional<Car> carOptional = carRepository.findById(carId);
        if(carOptional.isPresent()){
            Car car = carOptional.get();
            List<CarCategory> carCategoryList = carCategoryRepository.getCarCategoriesByCar(car);

            List<CategoryDTO> categoryDTOList = new ArrayList<>();
            for(CarCategory carCategory : carCategoryList){
                categoryDTOList.add(categoryMapper.fromCategoryToDTO(carCategory.getCategory()));
            }
            return categoryDTOList;
        }else{
            return null;
        }
    }

    public void deleteByLabel(String label){
        List<Category> categories = categoryRepository.getCategoriesByLabel(label);

        if(categories != null){
            categoryRepository.deleteAll(categories);
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
