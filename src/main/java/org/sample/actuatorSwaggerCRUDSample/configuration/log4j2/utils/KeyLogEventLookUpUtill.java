package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.utils;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class KeyLogEventLookUpUtill {
    private static Map<String, ByKeyOrLogEventValueExtractionUtil> BY_KEY_LOG_EVENT_IMPLS_MAP;

    public static Map<String, ByKeyOrLogEventValueExtractionUtil> BY_KEY_LOG_EVENT_IMPLS_MAP(){
        if(Objects.isNull(BY_KEY_LOG_EVENT_IMPLS_MAP))
            INIT_CUSTOM_EXTRACTION_MAP();
        return BY_KEY_LOG_EVENT_IMPLS_MAP;
    }

    private static void INIT_CUSTOM_EXTRACTION_MAP(){
        BY_KEY_LOG_EVENT_IMPLS_MAP = new HashMap<>();
        BY_KEY_LOG_EVENT_IMPLS_MAP.put("source.class.method",sourceClassMethodExtraction());
        BY_KEY_LOG_EVENT_IMPLS_MAP.put("request.identifier",requestIdentifierExtraction());
        BY_KEY_LOG_EVENT_IMPLS_MAP.put("trace.order",traceOrderExtraction());
    }

    private static ByKeyOrLogEventValueExtractionUtil sourceClassMethodExtraction(){
        return (LogEvent event) -> event.getSource().getClassName() + "." + event.getSource().getMethodName() + "(), line: " +event.getSource().getLineNumber();
    }

    private static ByKeyOrLogEventValueExtractionUtil traceOrderExtraction(){
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

    private static ByKeyOrLogEventValueExtractionUtil requestIdentifierExtraction(){
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