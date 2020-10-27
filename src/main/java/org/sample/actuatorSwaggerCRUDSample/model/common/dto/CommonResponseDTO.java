package org.sample.actuatorSwaggerCRUDSample.model.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


import java.time.Instant;
import java.time.LocalDateTime;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;



public class CommonResponseDTO<Data>{
    private Integer status;
    private Long timestamp;
    private String service;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateStamp;
    private Data data;
    private final List<CommonMessageDTO> messages;
    private final List<ErrorDesriptor> errorDescriptorList;

    public CommonResponseDTO() {
        this.messages = Collections.synchronizedList(new LinkedList());
        this.errorDescriptorList = Collections.synchronizedList(new LinkedList());
    }

    public CommonResponseDTO(String infoBuildArchiveBaseName) {
        this();
        this.service =infoBuildArchiveBaseName;
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

    public LocalDateTime getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(LocalDateTime dateStamp) {
        this.dateStamp = dateStamp;
    }

    public List<CommonMessageDTO> getMessages() {
        return messages;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public List<ErrorDesriptor> getErrorDescriptorList() {
        return errorDescriptorList;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public void setStatusCodeMessageDtoDataAndInitDate(final int httpStatusCode,
                                 final CommonMessageDTO commonMessageDTO,
                                 final Data data){
        this.setStatus(httpStatusCode);
        this.getMessages().add(commonMessageDTO);
        this.setData(data);
        this.setDateStamp(LocalDateTime.now());
        this.setTimestamp(Instant.now().getEpochSecond());
    }

    public void setStatusCodeMessageDtoErrorDescriptorAndInitDate(final int httpStatusCode,
                                                       final CommonMessageDTO commonMessageDTO,
                                                       final ErrorDesriptor errorDesriptor){
        this.setStatus(httpStatusCode);
        this.getMessages().add(commonMessageDTO);
        this.getErrorDescriptorList().add(errorDesriptor);
        this.setDateStamp(LocalDateTime.now());
        this.setTimestamp(Instant.now().getEpochSecond());
    }
}
