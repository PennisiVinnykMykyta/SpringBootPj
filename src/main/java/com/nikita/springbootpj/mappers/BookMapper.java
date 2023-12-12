package com.nikita.springbootpj.mappers;


import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.BookRequestDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.BookState;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper mapper;
    private final UserMapper userMapper;
    private final CarMapper carMapper;



    public Book fromRequestDtoToBook(User user, Car car, BookRequestDTO bookRequestDTO){
        Book book = null;
        if(bookRequestDTO != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            book = mapper.map(bookRequestDTO,Book.class);
            book.setUser(user);
            book.setCar(car);
            book.setState(BookState.STANDBY);
        }
        return book;
    }

    public BookDTO fromBookToDTO(Book book){
        BookDTO bookDTO = null;
        if(book != null){
            mapper.getConfiguration().setAmbiguityIgnored(true);
            bookDTO = mapper.map(book,BookDTO.class);
            bookDTO.setUser(userMapper.fromUserToDTO(book.getUser()));
            bookDTO.setCar(carMapper.fromCarToDTO(book.getCar()));
        }
        return bookDTO;
    }

    public void updateBook(Book book,BookRequestDTO bookRequestDTO,Car car) {
       if(book != null && bookRequestDTO != null){
            book.setStartDate(bookRequestDTO.getStartDate());
            book.setEndDate(bookRequestDTO.getEndDate());
            book.setCar(car);
            book.setState(BookState.STANDBY);
        }

    }
}
