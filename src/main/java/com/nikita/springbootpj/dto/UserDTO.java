package com.nikita.springbootpj.dto;

import lombok.Data;

import java.time.LocalDate;



@Data
public class UserDTO {

    private Integer id;
    private String userType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private LocalDate birthDate;

}
