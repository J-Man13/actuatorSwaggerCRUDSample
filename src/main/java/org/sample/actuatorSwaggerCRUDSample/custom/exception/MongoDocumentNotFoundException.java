package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class MongoDocumentNotFoundException extends GlobalCommonException {

    public MongoDocumentNotFoundException(String messageKey, String message, Throwable cause){
        super(messageKey,message,cause);
    }
}
