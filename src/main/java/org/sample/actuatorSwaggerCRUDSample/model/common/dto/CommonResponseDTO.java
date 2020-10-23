package org.sample.actuatorSwaggerCRUDSample.model.common.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;


import java.time.LocalDateTime;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class CommonResponseDTO<Data>{
    private Integer status;
    private Long timestamp;
    private String app;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateStamp;
    private Data data;
    private List<CommonMessageDTO> messages;
    private List<ErrorDesriptor> errorDescriptorList;

    public CommonResponseDTO() {
        this.messages = Collections.synchronizedList(new LinkedList());
        this.errorDescriptorList = Collections.synchronizedList(new LinkedList());
    }

    public CommonResponseDTO(String infoBuildArchiveBaseName) {
        this();
        this.app=infoBuildArchiveBaseName;
    }

    public CommonResponseDTO(int status) {
        this();
        this.status = status;
    }

    public CommonResponseDTO(int status, CommonMessageDTO commonMessageDTO) {
        this(status);
        messages.add(commonMessageDTO);
    }

    public CommonResponseDTO(int status, String type,String messageKey, String message) {
        this(status,new CommonMessageDTO(type,messageKey,message));
    }

    public CommonResponseDTO(int status, CommonMessageDTO commonMessageDTO,ErrorDesriptor errorDesriptor) {
        this(status,commonMessageDTO);
        errorDescriptorList.add(errorDesriptor);
    }

    public CommonResponseDTO(int status, String type, String messageKey, String message,ErrorDesriptor errorDesriptor) {
        this(status,new CommonMessageDTO(type,messageKey,message),errorDesriptor);
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

    public void setMessages(List<CommonMessageDTO> messages) {
        this.messages = messages;
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

    public void setErrorDescriptorList(List<ErrorDesriptor> errorDescriptorList) {
        this.errorDescriptorList = errorDescriptorList;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
