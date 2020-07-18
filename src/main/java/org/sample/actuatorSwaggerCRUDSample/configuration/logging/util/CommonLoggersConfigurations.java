package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;

import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonLoggersConfigurations {

    @Bean("requests-logger")
    public CommonLogger getRequestLogger(){
        return new CommonLogger(LogManager.getLogger("requests_logs"));
    }

    @Bean("trace-logger")
    public CommonLogger getTraceLogger(){
        return new CommonLogger(LogManager.getLogger("trace_logs"));
    }
}
