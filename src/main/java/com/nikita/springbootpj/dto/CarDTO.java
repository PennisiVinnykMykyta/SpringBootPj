package com.nikita.springbootpj.dto;

import lombok.Data;

@Data
public class CarDTO {

    private Integer id;
    private String numberPlate;
    private String color;
    private String model;
    private String brand;
    private String image;
    private String imageType;
}
