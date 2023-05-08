package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/*
Інтерфейс UserRepository розширює JpaRepository
JpaRepository - інтерфейс з додатковими методами роботи з БД
findByUsername - метод пошуку за ім'ям
 */
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
