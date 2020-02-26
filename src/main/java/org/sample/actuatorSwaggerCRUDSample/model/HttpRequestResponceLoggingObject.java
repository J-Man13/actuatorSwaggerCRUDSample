package org.sample.actuatorSwaggerCRUDSample.model;

import com.fasterxml.jackson.databind.ObjectMapper;


public class HttpRequestResponceLoggingObject{
    public HttpRequestFields request;
    public HttpResponseFields response;

    public HttpRequestResponceLoggingObject(HttpRequestFields request, HttpResponseFields responce) {
        this.request = request;
        this.response = responce;
    }

    public HttpRequestFields getRequest() {
        return request;
    }

    public void setRequest(HttpRequestFields request) {
        this.request = request;
    }

    public HttpResponseFields getResponse() {
        return response;
    }

    public void setResponse(HttpResponseFields response) {
        this.response = response;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        }catch (com.fasterxml.jackson.core.JsonProcessingException e) {
            e.printStackTrace();
        }
        return "HttpRequestResponceLoggingObject{" +
                "request=" + request +
                ", responce=" + response +
                '}';
    }
}
