package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.UserDTO;

import java.text.ParseException;
import java.util.List;

public interface UserService {

    String getUserHomepage(String mail);
    UserDTO getUserById(int id);
    UserDTO getUserByCredentials(String email);
    List<UserDTO> getAllUsers();
    String deleteUserById(int deleteID);

    String saveOrUpdateUser(UserDTO userDTO) throws ParseException;
}
