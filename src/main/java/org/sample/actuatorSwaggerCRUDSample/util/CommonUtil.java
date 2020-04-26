package org.sample.actuatorSwaggerCRUDSample.util;


import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


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
}
