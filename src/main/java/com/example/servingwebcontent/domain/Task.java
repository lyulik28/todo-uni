package com.example.servingwebcontent.domain;

import jakarta.persistence.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.sql.Date;
import java.util.List;

/*
Клас Task відповідає таблиці БД
Поля відповідають стовпцям
id - первиний ключ, генерується автоматично шляхом збільшення значення
@Temporal(TemporalType.DATE) для вказання даних у БД у вигляді дати
Створюється зв'язок з таблицєю users - Many-To-Many
Методи - це конструктори, гететри та сетери
Обо'язково має бути конструктор без параметрів, інакше Spring не зможе створювати об'єкт
 */
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String name;

    @Column
    @Temporal(TemporalType.DATE)
    private Date deadline;

    @Column
    private String description;

    @Column(columnDefinition = "boolean default 0")
    private boolean isCompleted;

    @ManyToMany(cascade = {CascadeType.PERSIST,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.DETACH})
    @JoinTable(
            name = "user_task",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Task(String name, String deadline, String description) {
        this.name = name;
        this.deadline = Date.valueOf(deadline);
        this.description = description;
    }

    public Task() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}
