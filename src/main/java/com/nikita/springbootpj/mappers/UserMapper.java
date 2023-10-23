package com.nikita.springbootpj.mappers;

import com.nikita.springbootpj.dto.UserDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.UserType;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class UserMapper {
    private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public UserDTO fromUserToDto(User user) {

        String id = Integer.toString(user.getId());
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String password = user.getPassword();
        String brithDate = format.format(user.getBirthDate());
        String userType;
        if (user.getType() == UserType.ADMIN) {
            userType = "admin";
        } else {
            userType = "customer";
        }
        List<Book> bookList = user.getBookings();

        return new UserDTO(id, firstName, lastName, email, password, userType, brithDate, bookList);

    }

    public User fromDtoToUser(UserDTO userDTO) throws ParseException {

        Integer id = Integer.parseInt(userDTO.getId());
        String firstName = userDTO.getFirstName();
        String lastName = userDTO.getLastName();
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        Date brithDate = format.parse(userDTO.getBirthDate());
        UserType userType;

        if (userDTO.getUserType().equals("admin")) {
            userType = UserType.ADMIN;
        } else {
            userType = UserType.CUSTOMER;
        }

        return new User(id, firstName, lastName, email, password, userType, brithDate);

    }


}
