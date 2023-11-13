package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

    User getUserByEmail(String email);

    boolean existsByEmailAndPassword(String email,String password);
}
