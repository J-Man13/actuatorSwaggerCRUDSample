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
    private final String date;
    private String level;
    private String desc;
    private AbstractMap.SimpleEntry<String,Object> entry;
    private String location;
    private Map<String,String> logMap;

    private final String hostName;
    private final String hostAddress;
    private final String appName;

    private final String activityId;
    private final String correlationId;
    public static CommonLoggingPropertiesConfig commonLoggingPropertiesConfig;



    public CommonLoggingObject() {
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        this.hostName = commonLoggingPropertiesConfig.getHostName();
        this.hostAddress =commonLoggingPropertiesConfig.getHostAddress();
        this.appName =commonLoggingPropertiesConfig.getInfoBuildArchiveBaseName();
        this.activityId = ThreadContext.get("activity.id");
        this.correlationId = ThreadContext.get("correlation.id");
    }

    public CommonLoggingObject(String desc, String location, String level) {
        this();
        this.desc = desc;
        this.location = location;
        this.level = level;
    }

    public CommonLoggingObject(String desc, String loggableObjectKey, Object data, String location, String level) {
        this(desc,location,level);
        if (StringUtils.isEmpty(loggableObjectKey) || data == null)
            this.entry = null;
        else
            this.entry = new AbstractMap.SimpleEntry<>(loggableObjectKey,data);
    }

    public CommonLoggingObject(String desc, Map<String,String> logMap, String location, String level) {
        this(desc,location,level);
        if (logMap == null || logMap.isEmpty())
            this.logMap = null;
        else
            this.logMap = logMap;
    }

    public String getDate() {
        return date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public AbstractMap.SimpleEntry<String, Object> getEntry() {
        return entry;
    }

    public void setEntry(AbstractMap.SimpleEntry<String, Object> entry) {
        this.entry = entry;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Map<String, String> getLogMap() {
        return logMap;
    }

    public void setLogMap(Map<String, String> logMap) {
        this.logMap = logMap;
    }

    public String getHostName() {
        return hostName;
    }

    public String getHostAddress() {
        return hostAddress;
    }

    public String getAppName() {
        return appName;
    }

    public String getActivityId() {
        return activityId;
    }

    public String getCorrelationId() {
        return correlationId;
    }
}
