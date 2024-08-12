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
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class TicketProcessor {

    private final TicketReader reader;

    // Минимальное время полета между городами для каждого авиаперевозчика
    public Map<String, String> calculateMinimumFlightTimeBetweenCitiesForEachAirCarrier(
            String startCity, String endCity
    ) {
        var tickets = reader.readFromFile().stream()
                .filter(t -> t.getOriginName().equals(startCity) &&
                        t.getDestinationName().equals(endCity))
                .toList();

        return tickets.stream()
                .collect(Collectors.toMap(
                        Ticket::getCarrier,
                        this::calculateFlightDuration,
                        this::minDuration
                ));
    }

    private String calculateFlightDuration(Ticket ticket) {
        var departure = LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime());
        var arrival = LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime());
        var duration = Duration.between(departure, arrival).toMinutes();
        return formatDuration(duration);
    }

    private String minDuration(String duration1, String duration2) {
        long minutes1 = parseDurationToMinutes(duration1);
        long minutes2 = parseDurationToMinutes(duration2);
        return minutes1 <= minutes2 ? duration1 : duration2;
    }

    private long parseDurationToMinutes(String duration) {
        String[] parts = duration.split(":");
        return Long.parseLong(parts[0]) * 60 + Long.parseLong(parts[1]);
    }

    private String formatDuration(long minutes) {
        long hours = minutes / 60;
        long remainingMinutes = minutes % 60;
        return String.format("%02d:%02d", hours, remainingMinutes);
    }

    // Разница между средней ценой и медианой для полета между городами
    public double calculateDifferenceBetweenAverageAndMedianPrices(String startCity, String endCity) {
        var ticketsPrice = reader.readFromFile().stream()
                .filter(t -> t.getOriginName().equals(startCity) &&
                        t.getDestinationName().equals(endCity))
                .map(Ticket::getPrice)
                .sorted()
                .toList();

        double average = ticketsPrice.stream()
                .mapToDouble(Double::valueOf)
                .average()
                .orElse(0.0);

        double median = calculateMedian(ticketsPrice);

        return Math.abs(average - median);
    }

    private double calculateMedian(List<Integer> prices) {
        int size = prices.size();
        if (size == 0) {
            return 0.0;
        }
        if (size % 2 == 0) {
            return (prices.get(size / 2) + prices.get(size / 2 - 1)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }
}
