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
            //Checking if logbook logging has been executed by extracting logbook.execution.status variable from
            //log4j2 thread context which was defined during logbook request logging
            //Logbook request/response logging can be disabled for some path in its configuration so
            //thread context wll not be cleared and activity.id with correlation.id will not be defined
            //which are cleared and defined in logbook per each request
            String logbookExecutionStatus = ThreadContext.get("logbook.execution.status");
            if (StringUtils.isEmpty(logbookExecutionStatus) || !logbookExecutionStatus.equals("executed")){
                ThreadContext.clearAll();
                String headerActivityId = request.getHeader("activity.id");
                String activityId = StringUtils.isEmpty(headerActivityId)?UUID.randomUUID().toString():headerActivityId;
                ThreadContext.put("activity.id",activityId);
                ThreadContext.put("correlation.id", UUID.randomUUID().toString());
            }
            return true;
        }
    }
}