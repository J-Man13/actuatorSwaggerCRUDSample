package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.utils;

import org.apache.logging.log4j.core.LogEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class KeyLogEventLookUpUtill {
    private static Map<String, ByKeyOrLogEventValueExtractionUtil> BY_KEY_LOG_EVENT_IMPLS_MAP;

    public static Map<String, ByKeyOrLogEventValueExtractionUtil> GET_BY_KEY_LOG_EVENT_IMPLS_MAP(){
        if(Objects.isNull(BY_KEY_LOG_EVENT_IMPLS_MAP))
            INIT_BY_KEY_LOG_EVENT_IMPLS_MAP();
        return BY_KEY_LOG_EVENT_IMPLS_MAP;
    }

    private static void INIT_BY_KEY_LOG_EVENT_IMPLS_MAP(){
        BY_KEY_LOG_EVENT_IMPLS_MAP = new HashMap<>();
        BY_KEY_LOG_EVENT_IMPLS_MAP.put("source.class.method",sourceClassMethodExtraction());
    }

    private static ByKeyOrLogEventValueExtractionUtil sourceClassMethodExtraction(){
        return (LogEvent event) -> event.getSource().getClassName() + "." + event.getSource().getMethodName() + "(), line: " +event.getSource().getLineNumber();
    }
}
