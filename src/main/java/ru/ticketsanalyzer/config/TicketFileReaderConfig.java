package ru.ticketsanalyzer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import ru.ticketsanalyzer.service.reader.TicketJsonReader;
import ru.ticketsanalyzer.service.reader.TicketReader;

@Configuration
public class TicketFileReaderConfig {
    @Bean
    @Primary
    @ConditionalOnProperty(name = "application.tickets.source-file.type", havingValue = "json")
    public ObjectMapper objectMapper() {
        var mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    @Bean
    @ConditionalOnProperty(name = "application.tickets.source-file.type", havingValue = "json")
    public TicketReader ticketJsonReader(ObjectMapper mapper) {
        return new TicketJsonReader(mapper);
    }
}
