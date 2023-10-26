package com.nikita.springbootpj.dto;

import com.nikita.springbootpj.entities.Book;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class UserDTO {

    private Integer id;
    private String userType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Date birthDate;

    private List<Book> bookList;

}
