package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class BookMapper {
/*
    public BookDTO fromBookToBookDTO(Book book) {
        CarMapper carMapper = new CarMapper();
        UserMapper userMapper = new UserMapper();

        int id = book.getId();
        LocalDate startDate = book.getStartDate();
        LocalDate endDate = book.getEndDate();
        User user = book.getUser();
        Car car = book.getCar();
        Boolean valid = book.getValid();

        UserDTO userDTO = userMapper.fromUserToDto(user);
        CarDTO carDTO = carMapper.fromCarToCarDTO(car);

        return new BookDTO(id, userDTO, carDTO, startDate, endDate, valid);

    }*/
}
