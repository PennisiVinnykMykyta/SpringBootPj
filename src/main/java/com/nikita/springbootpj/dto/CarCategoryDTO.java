package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarCategoryDTO {

    private Integer id;
    private CarDTO car;
    private CategoryDTO category;
}
