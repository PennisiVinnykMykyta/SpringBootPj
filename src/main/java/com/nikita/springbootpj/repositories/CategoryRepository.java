package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category,Integer> {

    List<Category> getCategoriesByLabel(String label);

    Category getCategoryByAttributeAndLabel(String attribute, String label);

    Optional<Category> findCategoryByAttributeAndLabel(String attribute, String label);

    Category getCategoryByAttribute(String attribute);


}
