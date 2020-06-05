package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;


import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.zalando.logbook.*;


import java.io.IOException;


import java.util.*;

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
        final String correlationId = precorrelation.getId();

        final Map<String, Object> content = new LinkedHashMap<>();

        content.put("origin", request.getOrigin().name().toLowerCase(Locale.ROOT));
        content.put("type", "request");
        content.put("correlation", correlationId);
        content.put("protocol", request.getProtocolVersion());
        content.put("remote", request.getRemote());
        content.put("method", request.getMethod());
        content.put("uri", request.getRequestUri());
        content.put("path",request.getPath());
        content.put("headers",request.getHeaders().toString());
        prepareBody(request).ifPresent(body -> content.put("body", body));

        String incomingActivityId = CommonUtil.getHeaderValueByKey("activity.id");
        content.put("incomingActivityId", incomingActivityId);
        String activityId = StringUtils.isEmpty(incomingActivityId)? UUID.randomUUID().toString():incomingActivityId;

        ThreadContext.clearAll();
        ThreadContext.put("activity.id",activityId);
        ThreadContext.put("correlation.id",UUID.randomUUID().toString());
        ThreadContext.put("logbook.execution.status","executed");

        return content;
    }

    @Override
    public Map<String, Object> prepare(Correlation correlation, HttpResponse response) throws IOException {
        final Map<String, Object> content = new LinkedHashMap<>();
        content.put("origin", response.getOrigin().name().toLowerCase(Locale.ROOT));
        content.put("type", "response");
        content.put("correlation", correlation.getId());
        content.put("duration", correlation.getDuration().toMillis());
        content.put("protocol", response.getProtocolVersion());
        content.put("status", response.getStatus());

        content.put("headers",response.getHeaders().toString());
        prepareBody(response).ifPresent(body -> content.put("body", body));

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