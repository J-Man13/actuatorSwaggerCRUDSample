package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.zalando.logbook.*;


import java.io.IOException;



import java.util.Map;
import java.util.Optional;

@Component
public class CustomJsonHttpLogFormatter implements StructuredHttpLogFormatter {
    private final ObjectMapper objectMapper;

    public CustomJsonHttpLogFormatter() {
        this.objectMapper = new ObjectMapper();
    }

    public CustomJsonHttpLogFormatter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> prepare(Precorrelation precorrelation, HttpRequest request) throws IOException {
        final Map<String, Object> content = StructuredHttpLogFormatter.super.prepare(precorrelation,request);
        content.put("incomingActivityId", CommonUtil.getHeaderValueByKey("activity.id"));
        content.remove("headers");
        content.put("headers",request.getHeaders().toString());
        return content;
    }

    @Override
    public Map<String, Object> prepare(Correlation correlation, HttpResponse response) throws IOException {
        final Map<String, Object> content = StructuredHttpLogFormatter.super.prepare(correlation,response);
        content.remove("headers");
        content.put("headers",response.getHeaders().toString());
        return content;
    }

    @Override
    public Optional<Object> prepareBody(final HttpMessage message) throws IOException {
        return Optional.ofNullable(ObjectUtils.isEmpty(message)|| StringUtils.isEmpty(message.getBodyAsString())? null : message.getBodyAsString());
    }



    @Override
    public String format(final Map<String, Object> content) throws IOException {
        try{return objectMapper.writeValueAsString(content);}catch (IOException ioe){return content.toString();}
    }
}