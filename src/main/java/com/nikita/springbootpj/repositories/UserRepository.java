package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.Book;
import com.nikita.springbootpj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    User getUserByEmail(String email);

    boolean existsByEmail(String email);
}
