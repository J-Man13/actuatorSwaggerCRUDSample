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
    private final ObjectMapper objectMapper;

    public CustomHttpRequestResponseLogWriter(@Qualifier("requests-logger") CommonLogger LOGGER){
        this.LOGGER = LOGGER;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(request);
        LOGGER.info("request logging","request",jsonNode);
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            LOGGER.info("response logging","response",jsonNode);
        }
        finally{
            ThreadContext.clearAll();//clear ThreadContext after request processing
        }
    }
}
