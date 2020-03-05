package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;


@Configuration
public class LogbookConfiguration {

    private CustomHttpRequestResponseLogWriter customHttpRequestResponseLogWriter;
    private CustomJsonHttpLogFormatter customJsonHttpLogFormatter;

    public LogbookConfiguration(@Autowired CustomHttpRequestResponseLogWriter customHttpRequestResponseLogWriter,
                                @Autowired CustomJsonHttpLogFormatter customJsonHttpLogFormatter) {
        this.customHttpRequestResponseLogWriter = customHttpRequestResponseLogWriter;
        this.customJsonHttpLogFormatter = customJsonHttpLogFormatter;
    }

    @Bean
    public Logbook logbook(){
        return  Logbook.builder()
                .sink(new DefaultSink(
                        customJsonHttpLogFormatter,
                        customHttpRequestResponseLogWriter
                ))
                .build();
    }
}
