package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonLoggingObject{
    private final String logDateStamp;
    private String logCauseDescription;
    private AbstractMap.SimpleEntry<String,Object> logEntry;
    private String logCallLocation;
    private Map<String,String> logMap;

    private final String logHostName;
    private final String logHostAddress;
    private final String logInfoBuildArchiveBaseName;

    private final String activityId;
    private final String correlationId;
    public static CommonLoggingPropertiesConfig commonLoggingPropertiesConfig;



    public CommonLoggingObject() {
        this.logDateStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        this.logHostName = commonLoggingPropertiesConfig.getHostName();
        this.logHostAddress =commonLoggingPropertiesConfig.getHostAddress();
        this.logInfoBuildArchiveBaseName =commonLoggingPropertiesConfig.getInfoBuildArchiveBaseName();
        this.activityId = ThreadContext.get("activity.id");
        this.correlationId = ThreadContext.get("correlation.id");
    }

    public CommonLoggingObject(String logCauseDescription,String location) {
        this();
        this.logCauseDescription = logCauseDescription;
        this.logCallLocation = location;
    }

    public CommonLoggingObject(String logCauseDescription, String loggableObjectKey, Object data,String location) {
        this(logCauseDescription,location);
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

    public String getLogDateStamp() {
        return logDateStamp;
    }

    public String getLogHostName() {
        return logHostName;
    }

    public String getLogHostAddress() {
        return logHostAddress;
    }

    public String getLogInfoBuildArchiveBaseName() {
        return logInfoBuildArchiveBaseName;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
