package com.nikita.springbootpj.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class BookDTO {

    private int id;
    private UserDTO user;
    private CarDTO car;
    private LocalDate startDate;
    private LocalDate endDate;

    private Boolean valid;

}
