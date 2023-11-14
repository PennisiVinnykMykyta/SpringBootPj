package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.UserDTO;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserDTO getUserById(int id);
    UserDTO getUserByCredentials(String email,String password);
    List<UserDTO> getAllUsers();
    void deleteUserById(int deleteID);

    UserDTO getUserByEmail(String email);

    public Map<String, Object> authenticate(String email, String password);

    void saveOrUpdateUser(UserDTO userDTO) throws ParseException;
}
