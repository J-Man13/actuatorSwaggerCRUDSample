package org.sample.actuatorSwaggerCRUDSample.model;

import java.io.Serializable;

public class BodyLogginWrapper<JsonSerializedData> implements Serializable {
    public String originalBodyAsString;
    public JsonSerializedData jsonSerializedData;

    public BodyLogginWrapper() {

    }

    public BodyLogginWrapper(String originalBodyAsString) {
        this();
        this.originalBodyAsString = originalBodyAsString;
    }

    public BodyLogginWrapper(String originalBodyAsString, JsonSerializedData jsonSerializedData) {
        this(originalBodyAsString);
        this.jsonSerializedData = jsonSerializedData;
    }

    public String getOriginalBodyAsString() {
        return originalBodyAsString;
    }

    public void setOriginalBodyAsString(String originalBodyAsString) {
        this.originalBodyAsString = originalBodyAsString;
    }

    public JsonSerializedData getJsonSerializedData() {
        return jsonSerializedData;
    }

    public void setJsonSerializedData(JsonSerializedData jsonSerializedData) {
        this.jsonSerializedData = jsonSerializedData;
    }
}
