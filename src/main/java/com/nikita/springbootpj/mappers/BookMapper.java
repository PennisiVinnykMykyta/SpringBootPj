package com.nikita.springbootpj.mappers;


import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.entities.Book;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final ModelMapper mapper;
    private final UserMapper userMapper;
    private final CarMapper carMapper;

    public Book fromDtoToBook(BookDTO bookDTO){
        Book book = null;
        if(bookDTO != null){
            book = mapper.map(bookDTO,Book.class);
            book.setUser(userMapper.fromDtoToUser(bookDTO.getUser()));
            book.setCar(carMapper.fromDtoToCar(bookDTO.getCar()));
        }
        return book;
    }

    public BookDTO fromBookToDTO(Book book){
        BookDTO bookDTO = null;
        if(book != null){
            bookDTO = mapper.map(book,BookDTO.class);
            bookDTO.setUser(userMapper.fromUserToDTO(book.getUser()));
            bookDTO.setCar(carMapper.fromCarToDTO(book.getCar()));
        }
        return bookDTO;
    }

    public void updateBook(Book book,BookDTO bookDTO) {
        if(book != null && bookDTO != null){
            mapper.map(bookDTO,book);
        }
    }
}
