package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

/*
Клас Registration - контролер
Має поле userRepository, що утворюється автоматично

 */
@Controller
public class Registration {
    @Autowired
    private UserRepository userRepository;

    /*
    Метод обробки запиту Get відправленому на /registration
    Повертає сторінку registration
     */
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }

    /*
     Метод обробки запиту Post відправленому на /registration
     Приймає User та Map - модель
     Виконує пошук користувача за ім'ям
     При наявності його у БД додає до моделі повідомлення
     та повертає сторінку registration
     Якщо користувача ще не існує, зберігає та повертає /login
     */
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model){
    User userDb = userRepository.findByUsername(user.getUsername());

    if(userDb != null){
        model.put("message", "This user exists");
        return "registration";
    }
        user.setEnabled(true);
        userRepository.save(user);
        return "redirect:/login";
    }
}
