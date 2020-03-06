package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.utils;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class CustomLookUpConfiguration {

    @Bean("customExtractionMap")
    public Map<String, ByKeyOrLogEventValueExtractionUtil> customExtractionMap(){
        Map<String, ByKeyOrLogEventValueExtractionUtil> customExtractionMap = new HashMap<>();
        customExtractionMap.put("source.class.method",sourceClassMethodExtraction());
        customExtractionMap.put("request.identifier",requestIdentifierExtraction());
        customExtractionMap.put("trace.order",traceOrderExtraction());
        return customExtractionMap;
    }

    private ByKeyOrLogEventValueExtractionUtil sourceClassMethodExtraction(){
        return (LogEvent event) -> event.getSource().getClassName() + "." + event.getSource().getMethodName() + "(), line: " +event.getSource().getLineNumber();
    }

    private ByKeyOrLogEventValueExtractionUtil traceOrderExtraction(){
        return (LogEvent event) ->{
            int traceOrderInt;
            String traceOrderString = ThreadContext.get("trace.order");
            if (StringUtils.isEmpty(traceOrderString))
                traceOrderInt = 1;
            else
                traceOrderInt = Integer.parseInt(traceOrderString)+1;
            ThreadContext.put("trace.order",String.valueOf(traceOrderInt));
            return String.valueOf(traceOrderInt);
        };
    }

    private ByKeyOrLogEventValueExtractionUtil requestIdentifierExtraction(){
        return (LogEvent event) ->{
            String requestIdentifier = ThreadContext.get("request.identifier");
            if (StringUtils.isEmpty(requestIdentifier)) {
                requestIdentifier = UUID.randomUUID().toString();
                ThreadContext.put("request.identifier",requestIdentifier);
            }
            return requestIdentifier;
        };
    }
}
