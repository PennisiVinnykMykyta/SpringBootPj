package com.nikita.springbootpj.dto;


import com.nikita.springbootpj.entities.enums.UserType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthDTO {
    String userType;
    String token;
}
