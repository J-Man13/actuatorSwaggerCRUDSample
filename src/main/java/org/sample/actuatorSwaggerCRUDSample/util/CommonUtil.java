package org.sample.actuatorSwaggerCRUDSample.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

public class CommonUtil {

    public static Map<String,String> headersKeyValueMap(ServerHttpRequest serverHttpRequest){
        return headersKeyValueMap(((ServletServerHttpRequest) serverHttpRequest).getServletRequest());
    }

    public static Map<String,String> headersKeyValueMap(HttpServletRequest httpServletRequest){
        Map<String,String> map = new HashMap<>();
        Enumeration headerNames = httpServletRequest.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = (String)headerNames.nextElement();
            map.put(headerName,httpServletRequest.getHeader(headerName));
        }
        return map;
    }

    public static Map<String,String> headersKeyValueMap(ServerHttpResponse serverHttpResponse){
        return headersKeyValueMap(((ServletServerHttpResponse) serverHttpResponse).getServletResponse());
    }

    public static Map<String,String> headersKeyValueMap(HttpServletResponse httpServletResponse){
        Map<String,String> map = new HashMap<>();
        Collection<String> headerNames = httpServletResponse.getHeaderNames();
        for (String headerName:headerNames)
            map.put(headerName,httpServletResponse.getHeader(headerName));
        return map;
    }

}
