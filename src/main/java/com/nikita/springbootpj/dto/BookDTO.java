package com.nikita.springbootpj.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {

    private int id;
    private UserDTO user;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean valid;

}
