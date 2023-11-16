package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.UserAuthDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.dto.UserDetailsDTO;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserDTO getUserById(int id);
    UserDTO getUserByCredentials(String email,String password);
    List<UserDTO> getAllUsers();
    void deleteUserById(int deleteID);

    UserDTO getUserByEmail(String email);

    UserDetailsDTO getUserDetailsByCredentials(String email, String password);


    public UserAuthDTO authenticate(String email, String password);

    void saveOrUpdateUser(UserDTO userDTO) throws ParseException;
}
