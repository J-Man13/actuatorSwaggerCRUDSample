package org.sample.actuatorSwaggerCRUDSample.model;

import org.apache.logging.log4j.message.MapMessage;

import java.util.Map;

public class HttpRequestLogingModel extends MapMessage {
    private static final String REQUEST_BODY = "requestBody";

    public HttpRequestLogingModel(String requestBody, Map<String, String> headerKeyValuePairs) {
        this.put(REQUEST_BODY, requestBody);
        this.putAll(headerKeyValuePairs);
    }
}
