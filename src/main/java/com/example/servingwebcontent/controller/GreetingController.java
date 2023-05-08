package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.domain.Task;
import com.example.servingwebcontent.domain.User;
import com.example.servingwebcontent.repository.TaskRepository;
import com.example.servingwebcontent.repository.UserRepository;
import org.apache.catalina.LifecycleState;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/*
Клас GreetingController є контролером для обробки запитів
Має два поля, познчені як @Autowired для отримання компонентів у контролері автоматично
 */
@Controller
public class GreetingController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;


    /*
    Метод обробки запиту Get відправленому на /todo
    Приймає порожню модель - об'єкт для передачі даних від контролеру
    Знаходить id у місті змерігання SecurityContextHolder дані користувача, який виконав вхід,
    Через id знаходить юзера та його задання
    Записує завдання у модель
    Повертає сторіну todoList
     */
    @GetMapping("/todo")
    public String todo(Map<String, Object> model) {
        int userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString());
        User user = userRepository.findById(userId).get();
        Iterable<Task> task = user.getTasks();
        model.put("task", task);
        return "todoList";
    }

    /*
    Метод для обробки запиту Post
    Приймає дані із значення параметру запиту
    Знаходить id користувача, створює новє завдання, зберігає,
     додає до користувача та зберігає його
     Повертає перезавантажену сторінку /todo
     */
    @PostMapping("/todo")
    public String addTask(@RequestParam String name, @RequestParam String deadline, @RequestParam String description) {
        int userId = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0].toString());

        Task task = new Task(name, deadline, description);

        Task createdTask = taskRepository.save(task);

        User user = userRepository.findById(userId).get();

        user.addUserTask(createdTask);

        userRepository.save(user);

        return "redirect:/todo";
    }

    /*
    Метод для обробки запиту Delete відправленому /todo/{id}
    Приймає зазнечений у URL id завдання
    Видаляє завдання
    Повертає null
     */
    @DeleteMapping("/todo/{id}")
    public String deleteTask(@PathVariable("id") int id) {
        taskRepository.deleteById(id);
        return null;
    }

    /*
    Метод для обробки запиту Put відправленому /todo/{id}
    Приймає зазнечений у URL id завдання
    Знаходить завдання, встановлює для поля об'єкту isCompleted true
    Повертає null
     */
    @PutMapping("/todo/{id}")
    public String completeTask(@PathVariable("id") int id){
        Task task = taskRepository.findById(id).get();
        task.setCompleted(true);
        taskRepository.save(task);
        return null;
    }

    /*
    Метод Get на кореневому URL
    Повертає сторінку home
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }
}
