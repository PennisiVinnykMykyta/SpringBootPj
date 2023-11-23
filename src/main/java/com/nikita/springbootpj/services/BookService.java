package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.BookToModifyDTO;
import com.nikita.springbootpj.dto.CarDTO;

import java.time.LocalDate;
import java.util.List;

public interface BookService{

    BookDTO getBookById(int id);//

    List<BookDTO> getAllBooks();
    void acceptBooking(int id); //

    List<CarDTO> bookedCars(LocalDate startDate, LocalDate endDate);

    void deleteBookById(int id); //

    void saveOrUpdateBook(BookToModifyDTO bookRequestDTO); //

    List<BookDTO> getAllUserBooks(String id);

}
