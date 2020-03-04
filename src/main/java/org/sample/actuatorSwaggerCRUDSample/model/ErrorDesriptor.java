package org.sample.actuatorSwaggerCRUDSample.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.Instant;
import java.time.LocalDateTime;

public class ErrorDesriptor {
    private String source;
    private String cause;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dtstamp;
    private Long timestamp;

    public ErrorDesriptor() {
        this.dtstamp = LocalDateTime.now();
        this.timestamp = Instant.now().getEpochSecond();
    }

    public ErrorDesriptor(String source, String description) {
        this();
        this.source = source;
        this.description = description;
    }

    public ErrorDesriptor(String source, String description,String cause) {
        this(source,description);
        this.cause = cause;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(LocalDateTime dtstamp) {
        this.dtstamp = dtstamp;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

}
