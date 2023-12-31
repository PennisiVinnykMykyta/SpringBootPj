package com.nikita.springbootpj.controllers;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.BookToModifyDTO;
import com.nikita.springbootpj.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/booking")
public class BookController {
    private final BookService bookService;

    @GetMapping("/get/{bookId}")
    public ResponseEntity<BookDTO> getBook(@PathVariable("bookId") int id){
        BookDTO bookDTO = bookService.getBookById(id);
        if(bookDTO != null){
            return  new ResponseEntity<>(bookDTO, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<BookDTO> getAllBooks(){
        List<BookDTO> booksList = bookService.getAllBooks();
        if(booksList != null){
            return new ResponseEntity(booksList,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/list/by-user/{userId}")
    public ResponseEntity<BookDTO> getAllUserBooks(@PathVariable("userId") String id){
        List<BookDTO> booksList = bookService.getAllUserBooks(id);
        return new ResponseEntity(booksList,HttpStatus.OK);
    }

    @RequestMapping(value = "/add-or-update", method = {RequestMethod.PUT, RequestMethod.POST})
    public void addOrUpdateBook(@RequestBody BookToModifyDTO bookRequestDTO){
        bookService.saveOrUpdateBook(bookRequestDTO);
    }

    @DeleteMapping("/delete/{bookId}")
    public void deleteBook(@PathVariable("bookId") int id){
        bookService.deleteBookById(id);
    }

    @RequestMapping(value = "/accept", method = {RequestMethod.PUT, RequestMethod.POST})
    public void acceptBook(@RequestBody Integer id){
        bookService.acceptBooking(id);
    }
    @RequestMapping(value = "/decline", method = {RequestMethod.PUT, RequestMethod.POST})
    public void declineBook(@RequestBody Integer id){
        bookService.declineBooking(id);
    }
}
