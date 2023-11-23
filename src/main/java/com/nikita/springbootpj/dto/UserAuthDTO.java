package com.nikita.springbootpj.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthDTO {
    String userType;
    String token;
}
