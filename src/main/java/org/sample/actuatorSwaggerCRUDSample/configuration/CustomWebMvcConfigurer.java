package org.sample.actuatorSwaggerCRUDSample.configuration;

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
            //Checking if activityId and correlationId where set in log4j2 thread context
            //This check is required in request interceptor as activityId and correlationId might not be set
            //If logging for the path is disabled in logbook
            String activityId = ThreadContext.get("activity.id");
            String correlationId = ThreadContext.get("correlation.id");
            if (StringUtils.isEmpty(activityId) && StringUtils.isEmpty(correlationId)){
                activityId = request.getHeader("activity.id");
                if (StringUtils.isEmpty(activityId))
                    activityId = UUID.randomUUID().toString();
                ThreadContext.put("activity.id",activityId);
                ThreadContext.put("correlation.id", UUID.randomUUID().toString());
            }
            return true;
        }
    }
}