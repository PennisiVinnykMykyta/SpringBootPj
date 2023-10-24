package com.nikita.springbootpj.entities;

import com.nikita.springbootpj.entities.enums.UserType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
@Data
@Getter
@Setter
public class User implements Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "user_type", nullable = false)
    private UserType type;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,orphanRemoval = true)
    private List<Book> bookings;

    public User() {

    }

    public String getEmail() {
        return email;
    }

    public List<Book> getBookings() {
        return bookings;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserType getType() {
        return type;
    }

    public Date getBirthDate() {
        return birthDate;
    }


    public User(Integer id, String email, String password, String firstName, String lastName, UserType type, Date birthDate) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.type = type;
        this.birthDate = birthDate;
    }

}

