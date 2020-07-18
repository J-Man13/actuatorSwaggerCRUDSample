package org.sample.actuatorSwaggerCRUDSample.model;

public class CommonMessageDTO {
    private String type;
    private String messageKey;
    private String message;

    public CommonMessageDTO(String type, String messageKey, String message) {
        this.type = type;
        this.messageKey = messageKey;
        this.message = message;
    }

    public CommonMessageDTO() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }

}
