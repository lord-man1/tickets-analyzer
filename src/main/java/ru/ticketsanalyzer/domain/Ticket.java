package ru.ticketsanalyzer.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Ticket {
    @JsonProperty("origin")
    private String origin;

    @JsonProperty("origin_name")
    private String originName;

    @JsonProperty("destination")
    private String destination;

    @JsonProperty("destination_name")
    private String destinationName;

    @JsonProperty("departure_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd")
    private LocalDate departureDate;

    @JsonProperty("departure_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[H][HH]:mm")
    private LocalTime departureTime;

    @JsonProperty("arrival_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yy.MM.dd")
    private LocalDate arrivalDate;

    @JsonProperty("arrival_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "[H][HH]:mm")
    private LocalTime arrivalTime;

    @JsonProperty("carrier")
    private String carrier;

    @JsonProperty("stops")
    private int stops;

    @JsonProperty("price")
    private int price;
}
