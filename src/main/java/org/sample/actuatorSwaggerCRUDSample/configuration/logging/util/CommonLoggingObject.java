package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;


import org.springframework.util.StringUtils;

import java.util.AbstractMap;
import java.util.Map;

public class CommonLoggingObject{
    private String logCauseDescription;
    private String logCallLocation;
    private AbstractMap.SimpleEntry<String,Object> logEntry;
    private Map<String,String> logMap;

    public CommonLoggingObject() {
    }

    public CommonLoggingObject(String logCauseDescription,String location) {
        this();
        this.logCauseDescription = logCauseDescription;
        this.logCallLocation = location;
    }

    public CommonLoggingObject(String logCauseDescription, String loggableObjectKey, Object data,String location) {

        if (StringUtils.isEmpty(loggableObjectKey) || data == null)
            this.logEntry = null;
        else
            this.logEntry = new AbstractMap.SimpleEntry<>(loggableObjectKey,data);
    }

    public CommonLoggingObject(String logCauseDescription, Map<String,String> logMap, String location) {
        this(logCauseDescription,location);
        if (logMap == null || logMap.isEmpty())
            this.logMap = null;
        else
            this.logMap = logMap;
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

    public String getLogCallLocation() {
        return logCallLocation;
    }

    public void setLogCallLocation(String logCallLocation) {
        this.logCallLocation = logCallLocation;
    }

    public Map<String, String> getLogMap() {
        return logMap;
    }

    public void setLogMap(Map<String, String> logMap) {
        this.logMap = logMap;
    }
}
