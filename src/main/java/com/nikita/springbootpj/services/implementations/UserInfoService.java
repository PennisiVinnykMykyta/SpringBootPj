package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.UserType;
import com.nikita.springbootpj.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoService implements UserDetailsService {

    private final  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        User user = userRepository.getUserByEmail(email);

        org.springframework.security.core.userdetails.User.UserBuilder userBuilder = org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword());

        if(user.getType() == UserType.ADMIN){
            userBuilder.roles("ADMIN");
        }else{
            userBuilder.roles("CUSTOMER");
        }

        return userBuilder.build();
    }
}
