package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.BookRequestDTO;
import com.nikita.springbootpj.dto.BookToModifyDTO;
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

        return bookMapper.fromBookToDTO(bookRepository.findById(id).orElseThrow(()->new RuntimeException("not valid id")));

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
    public void saveOrUpdateBook(BookToModifyDTO bookToModifyDTO){
        System.out.println(bookToModifyDTO.getEmail());
        User user = userRepository.getUserByEmail(bookToModifyDTO.getEmail());
        Car car = carRepository.findById(bookToModifyDTO.getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));


        if(user.getId() != null){
            //molto pasticcioso ma per adesso inizializzo cosi il mio book da modificare poi dovro' aggiungere un mapper corretto
            BookRequestDTO bookRequestDto = new BookRequestDTO();
            bookRequestDto.setBookId(bookToModifyDTO.getBookId());
            bookRequestDto.setCarId(bookToModifyDTO.getCarId());
            bookRequestDto.setUserId(user.getId());
            bookRequestDto.setStartDate(bookToModifyDTO.getStartDate());
            bookRequestDto.setEndDate(bookToModifyDTO.getEndDate());


            if(bookToModifyDTO.getBookId() == null){
              bookRepository.save(bookMapper.fromRequestDtoToBook(user,car,bookRequestDto));
            }else{
              Optional<Book> optionalBook = bookRepository.findById(bookToModifyDTO.getBookId());
                if(optionalBook.isPresent()){
                  Book book = optionalBook.get(); //prendiamo l'entità associata
                  System.out.println(book);

                  bookMapper.updateBook(book,bookRequestDto,car);
                  bookRepository.save(book);
                }
            }
        }
    }

    public List<BookDTO> getAllUserBooks(String id){
        User user = userMapper.fromDtoToUser(userService.getUserByEmail(id));
       // User user = userMapper.fromDtoToUser(userService.getUserById(id));
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
