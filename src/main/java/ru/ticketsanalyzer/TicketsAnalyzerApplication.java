package ru.ticketsanalyzer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.ticketsanalyzer.service.TicketProcessor;

@SpringBootApplication
@RequiredArgsConstructor
public class TicketsAnalyzerApplication {
    private final TicketProcessor processor;

    public static void main(String[] args) {
        SpringApplication.run(TicketsAnalyzerApplication.class, args);
    }

    @PostConstruct
    public void workExample() {
        System.out.println(
                processor.calculateMinimumFlightTimeBetweenCitiesForEachAirCarrier("Владивосток", "Тель-Авив")
        );
        System.out.println(
                processor.calculateDifferenceBetweenAverageAndMedianPrices("Владивосток", "Тель-Авив")
        );
    }
}
