package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.mappers.UserMapper;
import com.nikita.springbootpj.repositories.UserRepository;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDTO getUserByCredentials(String email){
        UserDTO userDTO = null;
        if(userRepository.existsByEmail(email)){
            userDTO = userMapper.fromUserToDTO(userRepository.getUserByEmail(email));
        }
        return userDTO;
    }

    public UserDTO getUserById(int id){
        UserDTO userDTO = null;
        if(userRepository.findById(id).isPresent()){
            userDTO = userMapper.fromUserToDTO(userRepository.findById(id).get());
        }
        return userDTO;

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
        if(userDTO.getId() == null){
            userRepository.save(userMapper.fromDtoToUser(userDTO));

        }else if(userRepository.findById(userDTO.getId()).isPresent()){
                UserDTO userToModify = userMapper.fromDTOToModify(userDTO);
                userRepository.save(userMapper.fromDtoToUser(userToModify));
        }

    }

    public void deleteUserById(int id){
        UserDTO userDTO = this.getUserById(id);
        if(userDTO != null){
            userRepository.deleteById(userDTO.getId());
        }else{
            System.out.println("No such user exists");
        }
    }


}
