package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    List<User> findAll();

    Optional<User> findById(Long id);

    void updateUser(User user);

    void deleteById(Long id);

    Optional<User> findByEmail(String email);
}

