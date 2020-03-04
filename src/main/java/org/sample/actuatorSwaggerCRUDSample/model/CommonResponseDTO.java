package org.sample.actuatorSwaggerCRUDSample.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class CommonResponseDTO<Data> {
    private Integer status;
    private Long timestamp;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dtstamp;
    private List<CommonMessageDTO> messages;
    private Data data;

    public CommonResponseDTO() {
        this.timestamp = Instant.now().getEpochSecond();
        this.messages = new LinkedList<>();
        this.dtstamp =  LocalDateTime.now();
    }
    public CommonResponseDTO(int status) {
        this();
        this.status = status;
    }

    public CommonResponseDTO(int status, CommonMessageDTO commonMessageDTO) {
        this(status);
        messages.add(commonMessageDTO);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public LocalDateTime getDtstamp() {
        return dtstamp;
    }

    public void setDtstamp(LocalDateTime dtstamp) {
        this.dtstamp = dtstamp;
    }

    public List<CommonMessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<CommonMessageDTO> messages) {
        this.messages = messages;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
