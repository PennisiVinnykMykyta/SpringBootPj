package com.nikita.springbootpj.dto;

import com.nikita.springbootpj.entities.Book;
import lombok.Data;

import java.util.List;


@Data
public class UserDTO {

    private String id;
    private String userType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String birthDate;

    private List<Book> bookList;

}
