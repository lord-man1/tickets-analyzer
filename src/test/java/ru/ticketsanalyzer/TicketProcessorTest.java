package ru.ticketsanalyzer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.ticketsanalyzer.domain.Ticket;
import ru.ticketsanalyzer.service.TicketProcessor;
import ru.ticketsanalyzer.service.reader.TicketReader;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TicketProcessorTest {

    @MockBean
    private TicketReader ticketReader;

    @Autowired
    private TicketProcessor ticketProcessor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCalculateMinimumFlightTimeBetweenCitiesForEachAirCarrier() {
        var ticket1 = Ticket.builder()
                .originName("Владивосток")
                .destinationName("Тель-Авив")
                .departureDate(LocalDate.of(2018, 5, 12))
                .departureTime(LocalTime.of(16, 20))
                .arrivalDate(LocalDate.of(2018, 5, 12))
                .arrivalTime(LocalTime.of(22, 10))
                .carrier("S7")
                .build();
        var ticket2 = Ticket.builder()
                .originName("Владивосток")
                .destinationName("Тель-Авив")
                .departureDate(LocalDate.of(2018, 5, 12))
                .departureTime(LocalTime.of(17, 20))
                .arrivalDate(LocalDate.of(2018, 5, 13))
                .arrivalTime(LocalTime.of(17, 30))
                .carrier("S7")
                .build();
        var ticket3 = Ticket.builder()
                .originName("Владивосток")
                .destinationName("Тель-Авив")
                .departureDate(LocalDate.of(2018, 5, 12))
                .departureTime(LocalTime.of(12, 10))
                .arrivalDate(LocalDate.of(2018, 5, 12))
                .arrivalTime(LocalTime.of(18, 20))
                .carrier("TK")
                .price(12400)
                .build();

        var tickets = Arrays.asList(ticket1, ticket2, ticket3);

        when(ticketReader.readFromFile()).thenReturn(tickets);

        var result = ticketProcessor.calculateMinimumFlightTimeBetweenCitiesForEachAirCarrier(
                "Владивосток", "Тель-Авив"
        );

        assertEquals("06:10", result.get("TK"));
        assertEquals("05:50", result.get("S7"));
    }

    @Test
    public void testCalculateDifferenceBetweenAverageAndMedianPrices() {
        var ticket1 = Ticket.builder()
                .originName("Владивосток")
                .destinationName("Тель-Авив")
                .price(12400)
                .build();
        var ticket2 = Ticket.builder()
                .originName("Владивосток")
                .destinationName("Тель-Авив")
                .price(13100)
                .build();
        var ticket3 = Ticket.builder()
                .originName("Владивосток")
                .destinationName("Тель-Авив")
                .price(15300)
                .build();

        var tickets = Arrays.asList(ticket1, ticket2, ticket3);

        when(ticketReader.readFromFile()).thenReturn(tickets);

        var result = ticketProcessor.calculateDifferenceBetweenAverageAndMedianPrices(
                "Владивосток", "Тель-Авив"
        );

        assertEquals(500, result);
    }
}

