package com.nikita.springbootpj.controllers;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookController {
    private final BookService bookService;

    @GetMapping("getBook/{id}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("id") int id){
        BookDTO bookDTO = bookService.getBookById(id);
        if(bookDTO != null){
            return  new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/book-list")
    public ResponseEntity<BookDTO> getAllBooks(){
        List<BookDTO> booksList = bookService.getAllBooks();
        if(booksList != null){
            return new ResponseEntity(booksList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user-book-list/{id}")
    public ResponseEntity<BookDTO> getAllUserBooks(@PathVariable("id") int id){
        List<BookDTO> booksList = bookService.getAllUserBooks(id);
        if(booksList != null){
            return new ResponseEntity(booksList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/add-or-update-book", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateCar(@RequestBody BookDTO bookDTO) throws ParseException {
        bookService.saveOrUpdateBook(bookDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCar(@PathVariable("id") int id){
        bookService.deleteBookById(id);
    }

    @RequestMapping(value = "/accept-book/{id}", method = {RequestMethod.PUT, RequestMethod.POST})
    public void acceptOrDeclineBook(@PathVariable("id") int id){
        bookService.acceptBooking(id);
    }
}
