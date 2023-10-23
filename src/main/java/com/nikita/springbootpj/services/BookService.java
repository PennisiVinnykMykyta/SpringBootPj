package com.nikita.springbootpj.services;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.CarDTO;

import java.util.List;

public interface BookService {

    BookDTO findBookById(int id);

    public void deleteAllUserBookings(int deleteID);

    List<BookDTO> findAllBooks();

    public void acceptBooking(int id);

    List<CarDTO> getConflictingBookings(String startDate, String endDate);

    void deleteById(int id);

    void saveOrUpdateBook(int userID, int carID, String bookID, String startDate, String endDate);

    List<BookDTO> findAllUserBooks(int id);

    String checkDates(String startDate, String endDate);

    String errorCheck(String error);

}
