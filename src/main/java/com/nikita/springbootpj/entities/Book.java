package com.nikita.springbootpj.entities;

import com.nikita.springbootpj.entities.enums.BookState;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book implements Serializable{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "car_id")
    private Car car;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="start_date")
    private LocalDate startDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="end_date")
    private LocalDate endDate;

    @Column(name="state")
    private BookState state;
}
