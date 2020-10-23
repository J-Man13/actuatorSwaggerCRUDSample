package org.sample.actuatorSwaggerCRUDSample.util;


import org.apache.logging.log4j.ThreadContext;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonMessageDTO;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.CommonResponseDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.Supplier;


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
        String activityId = StringUtils.isEmpty(incomingActivityId)? UUID.randomUUID().toString():incomingActivityId;
        ThreadContext.put("activity.id",activityId);
        ThreadContext.put("correlation.id",UUID.randomUUID().toString());
    }

}
