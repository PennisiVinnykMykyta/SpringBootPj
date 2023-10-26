package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.UserDTO;

import java.text.ParseException;
import java.util.List;

public interface UserService {

    UserDTO getUserById(int id);
    UserDTO getUserByCredentials(String email);
    List<UserDTO> getAllUsers();
    void deleteUserById(int deleteID);

    void saveOrUpdateUser(UserDTO userDTO) throws ParseException;
}
