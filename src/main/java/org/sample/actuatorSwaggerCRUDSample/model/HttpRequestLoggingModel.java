package org.sample.actuatorSwaggerCRUDSample.model;



import java.util.List;
import java.util.Map;

public class HttpRequestLoggingModel {
    private String requestBody;
    private Map<String, List<String>> requestHeaderKeyValuePairs;

    public HttpRequestLoggingModel(String requestBody, Map<String, List<String>> requestHeaderKeyValuePairs) {
        this.requestBody = requestBody;
        this.requestHeaderKeyValuePairs = requestHeaderKeyValuePairs;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public Map<String, List<String>> getRequestHeaderKeyValuePairs() {
        return requestHeaderKeyValuePairs;
    }

    public void setRequestHeaderKeyValuePairs(Map<String, List<String>> requestHeaderKeyValuePairs) {
        this.requestHeaderKeyValuePairs = requestHeaderKeyValuePairs;
    }
}
