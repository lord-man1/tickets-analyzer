package ru.ticketsanalyzer.service.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.ticketsanalyzer.domain.Ticket;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class TicketJsonReader implements TicketReader {
    @Value("${application.tickets.source-file.path}")
    private String pathToFile;
    private final ObjectMapper objectMapper;

    @Override
    public List<Ticket> readFromFile() {
        var file = getClass().getClassLoader()
                .getResourceAsStream(pathToFile);
        try {
            var node = objectMapper.readTree(file);
            var tickets = objectMapper.convertValue(
                    node.get("tickets"), Ticket[].class
            );
            return Arrays.asList(tickets);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
