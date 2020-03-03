package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;


@Configuration
public class LogbookConfiguration {

    private CustomHttpRequestResponseLogWriter customHttpRequestResponseLogWriter;

    public LogbookConfiguration(@Autowired CustomHttpRequestResponseLogWriter customHttpRequestResponseLogWriter) {
        this.customHttpRequestResponseLogWriter = customHttpRequestResponseLogWriter;
    }

    @Bean
    public Logbook logbook(){
        return  Logbook.builder()
                .sink(new DefaultSink(
                        new CustomJsonHttpLogFormatter(),
                        customHttpRequestResponseLogWriter
                ))
                .build();
    }
}
