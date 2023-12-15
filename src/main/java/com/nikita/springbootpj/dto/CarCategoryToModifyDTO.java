package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarCategoryToModifyDTO {

    private Integer carCategoryId;
    private Integer carId;
    private Integer categoryId;
}
