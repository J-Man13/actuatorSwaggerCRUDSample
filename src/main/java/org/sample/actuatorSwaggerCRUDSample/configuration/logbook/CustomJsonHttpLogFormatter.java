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
        content.put("uri", request.getRequestUri());
        content.put("remote", request.getRemote());
        content.put("path",request.getPath());
        content.put("method", request.getMethod());
        content.put("headers",request.getHeaders().toString());
        prepareBody(request).ifPresent(body -> content.put("body", body));

        Map<String,List<String>> heaedersKeyValues = request.getHeaders();
        List<String> incomingActivityIdList = null;
        String incomingActivityId = null;
        if (heaedersKeyValues != null && !heaedersKeyValues.isEmpty())
            incomingActivityIdList = heaedersKeyValues.get("activity.id");
        if (incomingActivityIdList != null && !incomingActivityIdList.isEmpty())
            incomingActivityId = StringUtils.isEmpty(incomingActivityIdList.get(0))?null:incomingActivityIdList.get(0);
        content.put("incomingActivityId", incomingActivityId);
        ThreadContext.clearAll();
        CommonUtil.activityCorrelationContextInitialization(incomingActivityId);

        return content;
    }

    @Override
    public Map<String, Object> prepare(Correlation correlation, HttpResponse response) throws IOException {
        final Map<String, Object> content = new LinkedHashMap<>();
        content.put("duration", correlation.getDuration().toMillis());
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