package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookRequestDTO {

    private Integer bookId;
    private Integer userId;
    private Integer carId;
    private LocalDate startDate;
    private LocalDate endDate;

}
