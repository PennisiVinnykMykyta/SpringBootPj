package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.UserAuthDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.dto.UserDetailsDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface UserService {

    UserDTO getUserById(int id);
    UserDTO getUserByCredentials(String email,String password);
    List<UserDTO> getAllUsers();
    void deleteUserById(int deleteID);

    UserDTO getUserByEmail(String email);

    UserAuthDTO authenticate(String email, String password);

    void saveOrUpdateUser(UserDTO userDTO) throws ParseException;

    void uploadProfilePic(MultipartFile file,int userId) throws IOException;

    byte[] downloadProfilePic(int userId) throws IOException;


}
