package com.example.demo.student;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig
{
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository)
        {
            return args ->
            {
                Student john1 = new Student("John1 Smith",
                        LocalDate.of(2001, Month.JUNE, 13),
                        "John.Smith@example.com");
                Student john2 = new Student("John2 Smith",
                        LocalDate.of(2001, Month.JUNE, 13),
                        "John.Smith@example.com");
                Student john3 = new Student("John3 Smith",
                        LocalDate.of(2001, Month.JUNE, 13),
                        "John.Smith@example.com");
                Student john4 = new Student("John4 Smith",
                        LocalDate.of(2001, Month.JUNE, 13),
                        "John.Smith@example.com");
                Student john5 = new Student("John5 Smith",
                        LocalDate.of(2001, Month.JUNE, 13),
                        "John.Smith@example.com");

                repository.saveAll(List.of(john1, john2, john3, john4, john5));
            };
        }
}
