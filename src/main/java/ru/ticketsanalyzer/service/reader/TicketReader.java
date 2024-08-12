package ru.ticketsanalyzer.service.reader;

import ru.ticketsanalyzer.domain.Ticket;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface TicketReader {
    List<Ticket> readFromFile();
}
