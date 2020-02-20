package org.sample.actuatorSwaggerCRUDSample.model;

public class CommonMessageDTO {
    private String type;
    private String message;

    public CommonMessageDTO(String type, String message) {
        this.message = message;
        this.type = type;
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

    @Override
    public String toString() {
        return "CommonMessageDTO{" +
                "type='" + type + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
