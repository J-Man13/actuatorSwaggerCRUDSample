package org.sample.actuatorSwaggerCRUDSample.model;

import java.util.Map;

public class HttpResponseLoggingModel {
    public Integer status;
    public String body;
    public Map<String,String> headers;

    public HttpResponseLoggingModel(Integer status, String body, Map<String, String> headers) {
        this.status = status;
        this.body = body;
        this.headers = headers;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

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
