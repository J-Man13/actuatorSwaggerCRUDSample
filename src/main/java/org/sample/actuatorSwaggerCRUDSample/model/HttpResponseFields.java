package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.Map;

public class HttpResponseFields {
    public Map<String,String> headers;
    public String body;

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
