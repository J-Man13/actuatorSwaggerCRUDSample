package org.sample.actuatorSwaggerCRUDSample.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.util.StringUtils;

import java.util.AbstractMap;

public class CommonLoggingObject{
    private String logCauseDescription;
    private  AbstractMap.SimpleEntry<String,Object> logEntry;

    public CommonLoggingObject() {
    }

    public CommonLoggingObject(String logCauseDescription) {
        this.logCauseDescription = logCauseDescription;
        this.logEntry = null;
    }

    public CommonLoggingObject(String description, String loggableObjectKey, Object object) {
        this.logCauseDescription = description;
        if (StringUtils.isEmpty(loggableObjectKey) || object == null)
            this.logEntry = null;
        else {
            this.logEntry = new AbstractMap.SimpleEntry<>(loggableObjectKey,object);
        }
    }

    public String getLogCauseDescription() {
        return logCauseDescription;
    }

    public void setLogCauseDescription(String logCauseDescription) {
        this.logCauseDescription = logCauseDescription;
    }

    public AbstractMap.SimpleEntry<String, Object> getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(AbstractMap.SimpleEntry<String, Object> logEntry) {
        this.logEntry = logEntry;
    }
}
