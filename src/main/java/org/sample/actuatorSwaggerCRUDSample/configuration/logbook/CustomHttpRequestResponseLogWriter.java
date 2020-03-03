package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomHttpRequestResponseLogWriter implements HttpLogWriter {
    private final Logger LOGGER = LogManager.getLogger("requests_logs");

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        ThreadContext.put("request.identifier", UUID.randomUUID().toString());
        try {
            LOGGER.info(new ObjectMapper().readTree(request));
        }
        catch (IOException ioe){
            LOGGER.info(request);
        }
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        ThreadContext.put("request.identifier", UUID.randomUUID().toString());
        try {
            LOGGER.info(new ObjectMapper().readTree(response));
        }
        catch (IOException ioe){
            LOGGER.info(response);
        }
    }
}
