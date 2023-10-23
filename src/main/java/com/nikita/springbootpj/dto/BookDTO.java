package com.nikita.springbootpj.dto;

import java.time.LocalDate;


public class BookDTO {

    private int id;
    private UserDTO user;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean valid;

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookDTO() {
    }

    public BookDTO(int id, UserDTO user, CarDTO car, LocalDate startDate, LocalDate endDate, Boolean valid) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.valid = valid;
    }

    public BookDTO(UserDTO user, CarDTO car, LocalDate startDate, LocalDate endDate, Boolean valid) {
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.valid = valid;
    }
}
