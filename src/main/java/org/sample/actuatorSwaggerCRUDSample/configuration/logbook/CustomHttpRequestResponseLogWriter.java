package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;


@Component
public class CustomHttpRequestResponseLogWriter implements HttpLogWriter {
    private final Logger LOGGER = LogManager.getLogger("requests_logs");

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        String activityId = CommonUtil.getHeaderValueByKey("activity.id");
        if(!StringUtils.isEmpty(activityId))
            ThreadContext.put("activity.id",activityId);

        try {
            LOGGER.info(new ObjectMapper().readTree(request));
        }
        catch (IOException ioe){
            LOGGER.info(request);
        }
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        try {
            LOGGER.info(new ObjectMapper().readTree(response));
        }
        catch (IOException ioe){
            LOGGER.info(response);
        }
    }
}
