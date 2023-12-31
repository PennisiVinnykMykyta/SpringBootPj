package com.nikita.springbootpj.services.implementations;

import com.nikita.springbootpj.dto.BookDTO;
import com.nikita.springbootpj.dto.BookRequestDTO;
import com.nikita.springbootpj.dto.BookToModifyDTO;
import com.nikita.springbootpj.dto.CarDTO;
import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.Car;
import com.nikita.springbootpj.entities.User;
import com.nikita.springbootpj.entities.enums.BookState;
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
        List<Book> conflictingBooks = new ArrayList<>();

        List<Book> startIsBetween = bookRepository.getBooksByStartDateBetween(start,finish);
        List<Book> finishIsBetween = bookRepository.getBooksByEndDateBetween(start,finish);
        List<Book> startAndFinishInclude = bookRepository.getBooksByStartDateBeforeAndEndDateAfter(start, finish);
        conflictingBooks.addAll(startIsBetween);
        conflictingBooks.addAll(finishIsBetween);
        conflictingBooks.addAll(startAndFinishInclude);

        List<CarDTO> bookedCars = new ArrayList<>();
        for(Book book : conflictingBooks){
            if(book.getState() == BookState.APPROVED){  //la macchina non sara' prenotabile solo se la prenotazione e' stata gia' confermata
                bookedCars.add(carMapper.fromCarToDTO(book.getCar()));
            }
        }
        return bookedCars;
    }

    public BookDTO getBookById(int id){ //tested

        return bookMapper.fromBookToDTO(bookRepository.findById(id).orElseThrow(()->new RuntimeException("not valid id")));

    }

    public void acceptBooking(int id){
        if(bookRepository.findById(id).isPresent()){
            Book book = bookRepository.findById(id).get();
            book.setState(BookState.APPROVED);
            bookRepository.save(book);
        }
    }
    public void declineBooking(int id){
        if(bookRepository.findById(id).isPresent()){
            Book book = bookRepository.findById(id).get();
            book.setState(BookState.DISAPPROVED);
            bookRepository.save(book);
        }
    }

    public void deleteBookById(int id){
        BookDTO bookDTO = this.getBookById(id);
        if(bookDTO != null){
            bookRepository.deleteById(bookDTO.getId());
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

    public void saveOrUpdateBook(BookToModifyDTO bookToModifyDTO){
        User user = userRepository.getUserByEmail(bookToModifyDTO.getEmail());
        Car car = carRepository.findById(bookToModifyDTO.getCarId()).orElseThrow(() -> new RuntimeException("Car not found"));


        if(user.getId() != null){
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
                  Book book = optionalBook.get();

                  bookMapper.updateBook(book,bookRequestDto,car);
                  bookRepository.save(book);
                }
            }
        }
    }

    public List<BookDTO> getAllUserBooks(String id){
        User user = userMapper.fromDtoToUser(userService.getUserByEmail(id));
        List<BookDTO> userBooks = new ArrayList<>();

        if(bookRepository.getBooksByUser(user) != null){
            for(Book book : bookRepository.getBooksByUser(user)){
                userBooks.add(bookMapper.fromBookToDTO(book));
            }
        }
        return userBooks;
    }
}
