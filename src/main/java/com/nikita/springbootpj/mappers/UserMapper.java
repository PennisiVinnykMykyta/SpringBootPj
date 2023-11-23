package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class UserMapper {
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public User fromDtoToUser(UserDTO userDTO){
        User user = null;
        if(userDTO != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            user = mapper.map(userDTO,User.class);
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
        return user;
    }

    public UserDTO fromUserToDTO(User user){
        UserDTO userDTO = null;

        if(user != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            userDTO = mapper.map(user, UserDTO.class);
        }
        return userDTO;
    }

    public void updateUser(User user, UserDTO userDTO) {
        if(user != null && userDTO != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            mapper.map(userDTO, user);
            String password = passwordEncoder.encode(user.getPassword());
            user.setPassword(password);
        }
    }
}
