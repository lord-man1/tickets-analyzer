package ru.ticketsanalyzer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ticketsanalyzer.domain.Ticket;
import ru.ticketsanalyzer.service.reader.TicketReader;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TicketProcessor {
    private final TicketReader reader;

    //  Минимальное время полета между городами для каждого авиаперевозчика
    public Map<String, String> calculateMinimumFlightTimeBetweenCitiesForEachAirCarrier(
            String startCity, String endCity
    ) {
        Map<String, Long> result = new HashMap<>();
        Map<String, String> result1 = new HashMap<>();
        var tickets = reader.readFromFile();
        var filteredTickets = tickets.stream()
                .filter(t -> t.getOriginName().equals(startCity) &&
                        t.getDestinationName().equals(endCity))
                .toList();
        for (var ticket : filteredTickets) {
            var date1 = LocalDateTime.of(
                    ticket.getArrivalDate(),
                    ticket.getArrivalTime()
            );
            var date2 = LocalDateTime.of(
                    ticket.getDepartureDate(),
                    ticket.getDepartureTime()
            );
            var diff = Duration.between(date2, date1).toMinutes();
            result.merge(ticket.getCarrier(), diff, Math::min);
        }
        for (Map.Entry<String, Long> entry : result.entrySet()) {
            result1.put(entry.getKey(), format("%02d:%02d", entry.getValue() / 60, entry.getValue() % 60));
        }
        return result1;
    }

    // Разница между средней ценой и медианой для полета между городами
    public Double calculateDifferenceBetweenAverageAndMedianPrices(
            String startCity, String endCity
    ) {
        var tickets = reader.readFromFile();
        var filteredTickets = tickets.stream()
                .filter(t -> t.getOriginName().equals(startCity) &&
                        t.getDestinationName().equals(endCity))
                .toList();
        var ticketsPrice = filteredTickets.stream()
                .map(Ticket::getPrice)
                .sorted()
                .toList();
        var average = ticketsPrice.stream().mapToDouble(Double::valueOf).sum() / ticketsPrice.size();
        var median = 0;
        if (ticketsPrice.size() % 2 == 0) {
            median = (ticketsPrice.get(ticketsPrice.size() / 2) + ticketsPrice.get(ticketsPrice.size() / 2 - 1)) / 2;
        } else {
            median = ticketsPrice.get(ticketsPrice.size() / 2);
        }
        return Math.abs(average - median);
    }

}
