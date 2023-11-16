package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookToModifyDTO {
    private Integer bookId;
    private String email;
    private Integer carId;
    private LocalDate startDate;
    private LocalDate endDate;
}
