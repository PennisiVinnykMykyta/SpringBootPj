package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> getBooksByStartDateBetween(LocalDate start, LocalDate end);
    List<Book> getBooksByEndDateBetween(LocalDate start, LocalDate end);
}

//OrEndDateBetween