package com.example.servingwebcontent.repository;

import com.example.servingwebcontent.domain.Task;
import org.springframework.data.repository.CrudRepository;

/*
Інтерфейс TaskRepository розширює CrudRepository - інший інтерфейс
 з базовими методами для роботи з БД
 */
public interface TaskRepository extends CrudRepository<Task, Integer> {
}
