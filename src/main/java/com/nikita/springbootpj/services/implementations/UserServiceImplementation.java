package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.mappers.UserMapper;
import com.nikita.springbootpj.repositories.UserRepository;
import com.nikita.springbootpj.services.UserService;
import org.hibernate.sql.exec.ExecutionException;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    private UserMapper userMapper;

    public UserDTO getUserByCredentials(String email){
        return userMapper.fromUserToDTO(userRepository.getUserByEmail(email));
    }

    public UserDTO getUserById(int id){
        return userMapper.fromUserToDTO(userRepository.findById(id).get());
    }

    public List<UserDTO> getAllUsers(){
        List<User> userList = userRepository.findAll();
        List<UserDTO> users = new ArrayList<>();
        for(User user : userList){
            users.add(userMapper.fromUserToDTO(user));
        }
        return users;
    }

    public void saveOrUpdateUser(UserDTO userDTO){
        if(userDTO.getId() == null){ // if there is no id we save him
            userRepository.save(userMapper.fromDtoToUser(userDTO));
        }else{ // else we need to take the updated version and save it
            UserDTO modifyToModify = userMapper.fromUserToDTO(userRepository.findById(userDTO.getId()).get());
            modifyToModify = userDTO;
            userRepository.save(userMapper.fromDtoToUser(modifyToModify));
            //
        }
    }

    public void deleteUserById(int id){
        UserDTO userDTO = this.getUserById(id);
        if(userDTO != null){
            userRepository.deleteById(userDTO.getId());
        }else{
            throw new ExecutionException("Couldn't delete user with this id: " + id);
        }
    }


}
