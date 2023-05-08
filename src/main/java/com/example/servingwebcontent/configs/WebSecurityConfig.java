package com.example.servingwebcontent.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/*
Клас WebSecurityConfig
@Configuration надає можливість створення бінів
@EnableWebSecurity - налаштування безпеки
Поле dataSource - об'єкт інтерфейсу підключення до БД
@Autowired дозволяє отримати компонент у контролері автоматично
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private DataSource dataSource;

    /*
    Бін - об'єкт яким керує контейнер IoC
    Приймає об'єкт класу HttpSecurity для налаштування правил безпеки
    Вказує шляхи доступу для всіх користувачів, логінацію та вихід з акауту для всіх користувачів
    Повертає http.build() типу SecurityFilterChain - елемент обробки запитів
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/waves.png", "/home.png", "/registration").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login").permitAll()
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }


    /*
    Приймає об'єкт класу AuthenticationManagerBuilder для налаштування авутентифікації
    Налоштовує входження у БД, отримку даних, шифрування паролю
    Нічого не повертає
     */
    @Autowired
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {

        authenticationManagerBuilder.jdbcAuthentication().dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery(
                        "SELECT username, password, enabled FROM users WHERE username=?")
                .authoritiesByUsernameQuery(
                        "SELECT username, id FROM users WHERE username=?");
    }
}
