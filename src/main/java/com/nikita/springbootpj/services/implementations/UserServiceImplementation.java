package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.config.security.JwtProvider;
import com.nikita.springbootpj.dto.UserAuthDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.dto.UserDetailsDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.UserType;
import com.nikita.springbootpj.mappers.UserMapper;
import com.nikita.springbootpj.repositories.UserRepository;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImplementation implements UserService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserDetailsDTO getUserDetailsByCredentials(String email, String password){
        UserDTO userDTO = this.getUserByCredentials(email,password);

        if(userDTO != null){
            UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
            userDetailsDTO.setEmail(userDTO.getEmail());
            userDetailsDTO.setPassword(userDetailsDTO.getPassword());

            UserAuthDTO token = this.authenticate(email, password);

            return userDetailsDTO;
        }

        return null;
    }

    public UserDTO getUserByCredentials(String email,String password){
        UserDTO userDTO = null;
        if(userRepository.existsByEmailAndPassword(email,password)){
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

    @Override
    public UserDTO getUserByEmail(String email) {
        if(email != null ){
            return userMapper.fromUserToDTO(userRepository.getUserByEmail(email));
        }
        return null;
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

        }else{
            Optional<User> optionalUser = userRepository.findById(userDTO.getId());
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                userMapper.updateUser(user,userDTO);
                System.out.println(user);
                userRepository.save(user);
            }
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

    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserAuthDTO authenticate(String email, String password){
        User user = this.userRepository.getUserByEmail(email);

        if(!password.equals(user.getPassword())){ //this.passwordEncoder.matches(password,user.getPassword())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Not valid credentials");
        }

        UserAuthDTO userAuthDTO = new UserAuthDTO();
        Map<String,Object> claimMap= new HashMap<>(0);
        if(user.getType() == UserType.ADMIN){
            claimMap.put("userType","ADMIN");
            userAuthDTO.setUserType("ADMIN");
        }else{
            claimMap.put("userType","CUSTOMER");
            userAuthDTO.setUserType("CUSTOMER");
        }

        String jwt = jwtProvider.generateToken(claimMap,email);
        claimMap.put("token",jwt);

        userAuthDTO.setToken(jwt);
        System.out.println(userAuthDTO);
        return userAuthDTO;

    }


}
