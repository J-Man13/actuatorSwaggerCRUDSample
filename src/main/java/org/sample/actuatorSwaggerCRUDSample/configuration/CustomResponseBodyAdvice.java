package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.model.CommonLoggingObject;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class CustomResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private final String CLASS = this.getClass().getCanonicalName();
    private final Logger LOGGER = LogManager.getLogger("requests_logs");

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        String responceBody;
        if (body == null) responceBody = "";
        else responceBody = body.toString();
        CommonLoggingObject commonLoggingObject = new CommonLoggingObject(CLASS,"Http response body", responceBody);
        LOGGER.info(commonLoggingObject);
        response.getHeaders().set("requestIdentifier", ThreadContext.get("request.identifier"));
        return body;
    }
}
