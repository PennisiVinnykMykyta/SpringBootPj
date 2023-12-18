package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category,Integer> {

    Category getCategoryByLabel(String label);
}
