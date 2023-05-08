package com.example.servingwebcontent.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
Контролер для логики навігації
Реалізує WebMvcConfigurer для визначення візуалізації входу /login

Має лише один метод, який приймає дані інтерфейсу ViewControllerRegistry для повернення статичних об'єктів
Налаштовує виведення сторінки login при переходы на /login
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

}
