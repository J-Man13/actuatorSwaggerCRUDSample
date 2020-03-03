package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.zalando.logbook.HttpMessage;
import org.zalando.logbook.StructuredHttpLogFormatter;


import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@Component
public class CustomJsonHttpLogFormatter implements StructuredHttpLogFormatter {
    private final ObjectMapper mapper;

    public CustomJsonHttpLogFormatter() {
        this(new ObjectMapper());
    }

    public CustomJsonHttpLogFormatter(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Optional<Object> prepareBody(final HttpMessage message) throws IOException {
        if (message.getBodyAsString() == null)
            return Optional.ofNullable(message.getBodyAsString());
        else {
            try {
                return Optional.ofNullable(mapper.readTree(message.getBodyAsString()));
            }
            catch (IOException ioe){
                return Optional.ofNullable(message.getBodyAsString());
            }
        }
    }

    @Override
    public String format(final Map<String, Object> content) throws IOException {
        return mapper.writeValueAsString(content);
    }
}
