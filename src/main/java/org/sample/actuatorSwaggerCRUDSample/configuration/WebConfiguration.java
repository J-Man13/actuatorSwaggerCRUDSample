package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private RequestResponseLoggingAdapter requestResponseLoggingAdapter;

    public WebConfiguration(@Autowired RequestResponseLoggingAdapter requestResponseLoggingAdapter){
        this.requestResponseLoggingAdapter = requestResponseLoggingAdapter;
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(requestResponseLoggingAdapter);
    }
}
