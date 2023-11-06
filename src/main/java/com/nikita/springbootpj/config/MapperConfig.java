package com.nikita.springbootpj.config;

import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper mapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(UserDTO.class,User.class).addMappings(mapper -> mapper.skip(User::setId));
        modelMapper.typeMap(UserDTO.class, Car.class).addMappings(mapper -> mapper.skip(Car::setId));
        modelMapper.typeMap(UserDTO.class, Book.class).addMappings(mapper -> mapper.skip(Book::setId));
        return new ModelMapper();
    }
}
