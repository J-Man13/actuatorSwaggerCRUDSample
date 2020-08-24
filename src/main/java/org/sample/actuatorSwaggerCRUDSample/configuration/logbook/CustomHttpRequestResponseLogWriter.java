package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;


@Component
public class CustomHttpRequestResponseLogWriter implements HttpLogWriter {
    private final CommonLogger LOGGER;

    public CustomHttpRequestResponseLogWriter(@Autowired @Qualifier("requests-logger") CommonLogger LOGGER){
        this.LOGGER = LOGGER;
    }

    @Override
    public void write(Precorrelation precorrelation, String request){
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(request);
            LOGGER.info("request logging","request",jsonNode);
        }
        catch (IOException ioe){
            LOGGER.info("request log is not in json format, logging as a string","unparsableRequest",request);
        }
    }

    @Override
    public void write(Correlation correlation, String response){
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(response);
            LOGGER.info("response logging","response",jsonNode);
        }
        catch (IOException ioe){
            LOGGER.info("response log is not in json format, logging as a string","unparsableResponse",response);
        }
        ThreadContext.clearAll();//clear ThreadContext after request processing
    }
}
