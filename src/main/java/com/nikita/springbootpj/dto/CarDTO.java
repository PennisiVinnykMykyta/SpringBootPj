package com.nikita.springbootpj.dto;

public class CarDTO {

    private String id;
    private String numberPlate;
    private String color;
    private String model;
    private String brand;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public CarDTO(String id, String numberPlate, String color, String model, String brand) {
        this.id = id;
        this.numberPlate = numberPlate;
        this.color = color;
        this.model = model;
        this.brand = brand;
    }

    public CarDTO() {
    }
}
