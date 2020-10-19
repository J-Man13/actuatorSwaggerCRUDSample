package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;






import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.logging.log4j.ThreadContext;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;

import java.util.Map;

@JsonSerialize(using = CommonLoggingObject.CustomSerializer.class)
public class CommonLoggingObject{
    private final String date;
    private String level;

    private AbstractMap.SimpleEntry<String,Object> entry;
    private Map<String,String> logMap;

    private String desc;

    private String location;

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

    public static class CustomSerializer extends JsonSerializer<CommonLoggingObject> {
        @Override
        public void serialize(CommonLoggingObject commonLoggingObject, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeStartObject();
            if (!ObjectUtils.isEmpty(commonLoggingObject.getDate()))
                gen.writeStringField("date", commonLoggingObject.getDate());
            if (!ObjectUtils.isEmpty(commonLoggingObject.getLevel()))
                gen.writeStringField("level", commonLoggingObject.getLevel());

            if (ObjectUtils.isEmpty(commonLoggingObject.getLogMap()))
                gen.writeObjectField(commonLoggingObject.getAppName(), commonLoggingObject.getEntry());
            else if (ObjectUtils.isEmpty(commonLoggingObject.getEntry()))
                gen.writeObjectField(commonLoggingObject.getAppName(), commonLoggingObject.getLogMap());

            if (!ObjectUtils.isEmpty(commonLoggingObject.getDesc()))
                gen.writeStringField("desc", commonLoggingObject.getDesc());
            if (!ObjectUtils.isEmpty(commonLoggingObject.getHostName()))
                gen.writeStringField("hostName", commonLoggingObject.getHostName());
            if (!ObjectUtils.isEmpty(commonLoggingObject.getHostAddress()))
                gen.writeStringField("hostAddress", commonLoggingObject.getHostAddress());
            if (!ObjectUtils.isEmpty(commonLoggingObject.getAppName()))
                gen.writeStringField("appName", commonLoggingObject.getAppName());
            if (!ObjectUtils.isEmpty(commonLoggingObject.getActivityId()))
                gen.writeStringField("activityId", commonLoggingObject.getActivityId());
            if (!ObjectUtils.isEmpty(commonLoggingObject.getCorrelationId()))
                gen.writeStringField("correlationId", commonLoggingObject.getCorrelationId());
        }
    }
}
