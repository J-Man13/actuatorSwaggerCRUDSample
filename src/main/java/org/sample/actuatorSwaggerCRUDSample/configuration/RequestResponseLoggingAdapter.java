package org.sample.actuatorSwaggerCRUDSample.configuration;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.model.CommonLoggingObject;
import org.sample.actuatorSwaggerCRUDSample.model.HttpRequestLoggingModel;
import org.sample.actuatorSwaggerCRUDSample.model.HttpResponseBodyHolderBean;
import org.sample.actuatorSwaggerCRUDSample.model.HttpResponseLoggingModel;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
public class RequestResponseLoggingAdapter extends HandlerInterceptorAdapter {
    private static final Logger LOGGER = LogManager.getLogger("requests_logs");
    private static final String CLASS = RequestResponseLoggingAdapter.class.getCanonicalName();

    @Autowired
    @Qualifier("httpResponseBodyHolderBean")
    private HttpResponseBodyHolderBean httpResponseBodyHolderBean;

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws IOException {
        String requestBody = ByteSource.wrap(ByteStreams.toByteArray(request.getInputStream()))
                .asCharSource(Charsets.UTF_8).read();
        Map<String,String> headerKeyValuePairs = CommonUtil.headersKeyValueMap(request);

        HttpRequestLoggingModel httpRequestLoggingModel = new HttpRequestLoggingModel(request.getMethod(),request.getRequestURI(),requestBody,headerKeyValuePairs);
        CommonLoggingObject commonLoggingObject = new CommonLoggingObject(CLASS,"Http request body and headers logging", httpRequestLoggingModel);

        String requestIdentifier = UUID.randomUUID().toString();
        ThreadContext.put("request.identifier",requestIdentifier);
        LOGGER.info(commonLoggingObject);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex){
        Map<String,String> headerKeyValuePairs = CommonUtil.headersKeyValueMap(response);
        HttpResponseLoggingModel httpResponseLoggingModel = new HttpResponseLoggingModel(response.getStatus(),httpResponseBodyHolderBean.getResponseBody(),headerKeyValuePairs);
        LOGGER.info(new CommonLoggingObject(CLASS,"Http responce body and headers logging",httpResponseLoggingModel));
    }
}
