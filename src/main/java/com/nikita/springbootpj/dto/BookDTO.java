package com.nikita.springbootpj.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {

    private Integer id;
    private UserDTO user;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;

    private String bookState;

}
