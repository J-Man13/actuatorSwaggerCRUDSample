package org.sample.actuatorSwaggerCRUDSample.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.message.ObjectMessage;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RequestResponseLoggingAdapter extends HandlerInterceptorAdapter {

    private Logger logger = LogManager.getLogger("requests_logs");

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {
        String requestBody = ByteSource.wrap(ByteStreams.toByteArray(request.getInputStream()))
                .asCharSource(Charsets.UTF_8).read();
        Map<String,String> headerKeyValuePairs = CommonUtil.headersKeyValueMap(request);

        Map<String,String> httpRequestLogs = new HashMap<>();
        httpRequestLogs.putAll(headerKeyValuePairs);
        httpRequestLogs.put("requestBody",requestBody);
        httpRequestLogs.put("type","requestBodyAndHeaders");
        httpRequestLogs.put("method",request.getMethod());

        String requestIdentifier = UUID.randomUUID().toString();
        ThreadContext.put("request.identifier",requestIdentifier);
        logger.info(new ObjectMessage(httpRequestLogs));
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex){

        Map<String,String> headerKeyValuePairs = CommonUtil.headersKeyValueMap(response);
        Map<String,String> httpResponseLogs = new HashMap<>();
        httpResponseLogs.putAll(headerKeyValuePairs);
        httpResponseLogs.put("type","responseHeaders");
        logger.info(new ObjectMessage(httpResponseLogs));
    }

}
