package com.nikita.springbootpj.repositories;

import com.nikita.springbootpj.entities.User;

import java.util.List;

public interface UserRepository {

    void deleteById(int id);

    void saveOrUpdateUser(User user);

    List<User> findAll();

    User findById(int id);

    User findByEmail(String email);

}
