package org.sample.actuatorSwaggerCRUDSample.configuration;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.model.CommonLoggingObject;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.UUID;

@Component
public class RequestResponseHeadersLoggingAdapter extends HandlerInterceptorAdapter {
    private final Logger LOGGER = LogManager.getLogger("requests_logs");
    private final String CLASS = this.getClass().getCanonicalName();
    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler){
        String requestIdentifier = UUID.randomUUID().toString();
        ThreadContext.put("request.identifier",requestIdentifier);
        Map<String,String> requestHeaderKeyValuePairs = CommonUtil.headersKeyValueMap(request);
        CommonLoggingObject commonLoggingObject = new CommonLoggingObject(CLASS,"Http request headers logging", requestHeaderKeyValuePairs);
        LOGGER.info(commonLoggingObject);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex){
        Map<String,String> responseHeaderKeyValuePairs = CommonUtil.headersKeyValueMap(response);
        CommonLoggingObject commonLoggingObject = new CommonLoggingObject(CLASS,"Http response headers logging", responseHeaderKeyValuePairs);
        LOGGER.info(commonLoggingObject);
    }
}
