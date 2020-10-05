package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.message.Message;



public class CommonLoggableJsonMessage implements Message{
    private String messageString;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public CommonLoggableJsonMessage(){
        this(null);
    }

    public CommonLoggableJsonMessage(Object msgObj){
        parseMessageAsJson(msgObj);
    }

    private void parseMessageAsJson(Object msgObj){
        try {
            messageString = objectMapper.writeValueAsString(msgObj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFormattedMessage() {
        return messageString;
    }

    @Override
    public String getFormat() {
        return messageString;
    }

    @Override
    public Object[] getParameters() {
        return null;
    }

    @Override
    public Throwable getThrowable() {
        return null;
    }
}