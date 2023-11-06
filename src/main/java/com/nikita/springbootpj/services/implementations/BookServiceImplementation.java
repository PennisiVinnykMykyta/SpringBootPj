package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.mappers.BookMapper;
import com.nikita.springbootpj.mappers.CarMapper;
import com.nikita.springbootpj.mappers.UserMapper;
import com.nikita.springbootpj.repositories.BookRepository;
import com.nikita.springbootpj.repositories.CarRepository;
import com.nikita.springbootpj.repositories.UserRepository;
import com.nikita.springbootpj.services.BookService;
import com.nikita.springbootpj.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImplementation implements BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final BookMapper bookMapper;
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    public List<CarDTO> bookedCars(LocalDate start, LocalDate finish){
        List<Book> startIsBetween = bookRepository.getBooksByStartDateBetween(start,finish);
        List<Book> finishIsBetween = bookRepository.getBooksByEndDateBetween(start,finish);
        List<Book> conflictingBooks = new ArrayList<>();
        conflictingBooks.addAll(startIsBetween);
        conflictingBooks.addAll(finishIsBetween);

        List<CarDTO> bookedCars = new ArrayList<>();
        for(Book book : conflictingBooks){
            bookedCars.add(carMapper.fromCarToDTO(book.getCar()));
        }
        return bookedCars;
    }

    public BookDTO getBookById(int id){ //tested
        BookDTO bookDTO = null;
        if(bookRepository.findById(id).isPresent()){
            bookDTO = bookMapper.fromBookToDTO(bookRepository.findById(id).get());
        }
        return bookDTO;

    }

    public void acceptBooking(int id){
        if(bookRepository.findById(id).isPresent()){
            Book book = bookRepository.findById(id).get();
            if(book.getValid() != null){
                book.setValid(!book.getValid());
                bookRepository.save(book);
            }else{
                System.out.println("Couldn't change books validity status");
            }
        }
    }

    public void deleteBookById(int id){
        BookDTO bookDTO = this.getBookById(id);
        if(bookDTO != null){
            bookRepository.deleteById(bookDTO.getId());
        }else{
            System.out.println("Couldn't Delete book: " + id);
        }
    }

    public List<BookDTO> getAllBooks(){
        List<Book> bookList = bookRepository.findAll();
        List<BookDTO> books = new ArrayList<>();
        for(Book book : bookList){
            books.add(bookMapper.fromBookToDTO(book));
        }
        return books;
    }
//passo id da angular poi trovo le entità con quel id e li setto al mio book
    public void saveOrUpdateBook(BookDTO bookDTO){

        if(bookDTO.getId() == null){
            bookRepository.save(bookMapper.fromDtoToBook(bookDTO));
        }else{
            Optional<Book> optionalBook = bookRepository.findById(bookDTO.getId());
            if(optionalBook.isPresent()){
                BookDTO book = getBookById(bookDTO.getId()); //prendiamo l'entità associata
                System.out.println("Associated Book before the change");
                System.out.println(book);
                if(book != null){
                    book = bookMapper.updateBook(bookDTO); //aggiorniamo i dati del dto associato
                    System.out.println("Associated Book after the change");
                    System.out.println(book);
                    bookRepository.save(bookMapper.fromDtoToBook(book)); //salviamo l'entità dopo che la mappiamo dal dto
                }
                /*Car car = carRepository.findById(bookDTO.getCar().getId()).orElseThrow(() -> new RuntimeException("Car not found"));
                System.out.println("Car: "+car);
                Book book = optionalBook.get();
                System.out.println("PresentCar: "+book.getCar());
                bookMapper.updateBook(book,bookDTO,car);
                bookRepository.save(book);*/
            }
        }
    }

    public List<BookDTO> getAllUserBooks(int id){
        User user = userMapper.fromDtoToUser(userService.getUserById(id));
        List<BookDTO> userBooks = new ArrayList<>();

        if(bookRepository.getBooksByUser(user) != null){
            System.out.println("books not empty");
            for(Book book : bookRepository.getBooksByUser(user)){
                userBooks.add(bookMapper.fromBookToDTO(book));
            }
        }
        return userBooks;
    }
}
