package org.sample.actuatorSwaggerCRUDSample.configuration;

import org.sample.actuatorSwaggerCRUDSample.model.HttpResponseBodyHolderBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class CommonConfigurations {
    @Bean
    @RequestScope
    public HttpResponseBodyHolderBean httpResponseBodyHolderBean(){
        return new HttpResponseBodyHolderBean();
    }
}
