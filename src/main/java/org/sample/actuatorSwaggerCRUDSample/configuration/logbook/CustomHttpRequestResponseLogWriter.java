package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sample.actuatorSwaggerCRUDSample.model.CommonLoggingObject;
import org.springframework.stereotype.Component;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;


@Component
public class CustomHttpRequestResponseLogWriter implements HttpLogWriter {
    private final Logger LOGGER = LogManager.getLogger("requests_logs");

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(request);
            LOGGER.info(new CommonLoggingObject("request logging","request",jsonNode));
        }
        catch (IOException ioe){
            LOGGER.info(request);
        }
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(response);
            LOGGER.info(new CommonLoggingObject("response logging","response",jsonNode));
        }
        catch (IOException ioe){
            LOGGER.info(response);
        }
    }
}
