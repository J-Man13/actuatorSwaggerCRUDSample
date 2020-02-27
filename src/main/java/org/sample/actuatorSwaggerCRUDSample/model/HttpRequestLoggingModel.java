package org.sample.actuatorSwaggerCRUDSample.model;

import org.apache.logging.log4j.message.MapMessage;

import java.util.Map;

public class HttpRequestLoggingModel {
    private String method;
    private String path;
    private String requestBody;
    private Map<String,String> headerKeyValuePairs;

    public HttpRequestLoggingModel(String method, String path, String requestBody, Map<String, String> headerKeyValuePairs) {
        this.method = method;
        this.path = path;
        this.requestBody = requestBody;
        this.headerKeyValuePairs = headerKeyValuePairs;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, String> getHeaderKeyValuePairs() {
        return headerKeyValuePairs;
    }

    public void setHeaderKeyValuePairs(Map<String, String> headerKeyValuePairs) {
        this.headerKeyValuePairs = headerKeyValuePairs;
    }
}
