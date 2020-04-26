package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.apache.logging.log4j.ThreadContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            String activityId = request.getHeader("activity.id");
            if (!StringUtils.isEmpty(activityId) && StringUtils.isEmpty(ThreadContext.get("activity.id")))
                ThreadContext.put("activity.id",activityId);
            return true;
        }
    }
}