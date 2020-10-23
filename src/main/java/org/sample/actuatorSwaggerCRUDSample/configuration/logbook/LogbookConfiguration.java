package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.*;


import static org.zalando.logbook.Conditions.*;



@Configuration
public class LogbookConfiguration {

    private final CustomHttpRequestResponseLogWriter customHttpRequestResponseLogWriter;
    private final CustomJsonHttpLogFormatter customJsonHttpLogFormatter;

    public LogbookConfiguration(final CustomHttpRequestResponseLogWriter customHttpRequestResponseLogWriter,
                                final CustomJsonHttpLogFormatter customJsonHttpLogFormatter) {
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
                .condition(exclude(
                        requestTo("/actuator/**"),
                        requestTo("**/actuator/**"),
                        requestTo("/swagger**"),
                        requestTo("**/swagger**"),
                        requestTo("/favicon.ico"),
                        requestTo("**/favicon.ico"),
                        requestTo("**/v2/api-docs"),
                        requestTo("/webjars/**"),
                        requestTo("**/webjars/**")))
                .build();
    }
}
