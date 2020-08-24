package org.sample.actuatorSwaggerCRUDSample.configuration.mvc;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Configuration
public class CustomWebMvcConfigurer implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new CustomHttpRequestInterceptor());
    }

    class CustomHttpRequestInterceptor extends HandlerInterceptorAdapter {
        @Override
        public boolean preHandle(HttpServletRequest request,
                                 HttpServletResponse response, Object handler) throws Exception {
            //Logbook request/response logging can be disabled for some path in its configuration so
            //Checking if activity.id and correlation.id has been set or not in the beginning of the request
            //If they  are not set, setting them here
            if (StringUtils.isEmpty(ThreadContext.get("activity.id")) && StringUtils.isEmpty(ThreadContext.get("correlation.id"))){
                String headerActivityId = request.getHeader("activity.id");
                String activityId = StringUtils.isEmpty(headerActivityId)?UUID.randomUUID().toString():headerActivityId;
                ThreadContext.put("activity.id",activityId);
                ThreadContext.put("correlation.id", UUID.randomUUID().toString());
            }
            return true;
        }
    }
}