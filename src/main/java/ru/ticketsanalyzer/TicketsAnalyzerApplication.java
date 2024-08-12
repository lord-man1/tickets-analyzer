package ru.ticketsanalyzer;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TicketsAnalyzerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TicketsAnalyzerApplication.class, args);
        workExample();
    }

    public static void workExample() {
        System.out.println("hello world");
    }
}
