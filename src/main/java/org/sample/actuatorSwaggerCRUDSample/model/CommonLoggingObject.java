package org.sample.actuatorSwaggerCRUDSample.model;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CommonLoggingObject<Data>{
    private String source;
    private String description;
    @JsonProperty("loggableObjectClass")
    private String loggableObjectClass;
    private Data data;

    public CommonLoggingObject(String source,String description, Data data) {
        this.source = source;
        this.description = description;
        this.data = data;
        if (data == null)
            this.loggableObjectClass = null;
        else
            this.loggableObjectClass = data.getClass().getSimpleName();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
        if (data == null)
            this.loggableObjectClass = null;
        else
            this.loggableObjectClass = data.getClass().getSimpleName();
    }
}
