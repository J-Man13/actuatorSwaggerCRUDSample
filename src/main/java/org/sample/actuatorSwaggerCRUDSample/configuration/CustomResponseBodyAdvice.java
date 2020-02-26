package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private Logger logger = LogManager.getLogger("requests_logs");

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        Map<String,String> httpResponseBodyLogs = new HashMap<>();
        httpResponseBodyLogs.put("type","responseBody");
        httpResponseBodyLogs.put("response",body.toString());
        logger.info(httpResponseBodyLogs);
        String requestIdentifier = ThreadContext.get("request.identifier");
        response.getHeaders().set("requestIdentifier",requestIdentifier);
        return body;
    }
}
