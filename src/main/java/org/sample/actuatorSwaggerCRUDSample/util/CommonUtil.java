package org.sample.actuatorSwaggerCRUDSample.util;


import org.apache.logging.log4j.ThreadContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;


public class CommonUtil {

    public static String getHeaderValueByKey(String header) {
        try {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
            return servletRequest.getHeader(header);
        }
        catch (Exception exception){
            return null;
        }
    }

    public static void activityCorrelationContextInitialization(String incomingActivityId){
        ThreadContext.clearAll();
        String activityId = StringUtils.isEmpty(incomingActivityId)? UUID.randomUUID().toString():incomingActivityId;
        ThreadContext.put("activity.id",activityId);
        ThreadContext.put("correlation.id",UUID.randomUUID().toString());
        ThreadContext.put("logbook.execution.status","executed");
    }
}
