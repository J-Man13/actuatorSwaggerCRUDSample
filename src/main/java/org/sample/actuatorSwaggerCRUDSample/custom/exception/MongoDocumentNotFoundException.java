package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class MongoDocumentNotFoundException extends GlobalCommonException {

    public MongoDocumentNotFoundException(String messageKey,String messageEn, String message, Throwable cause){
        super(messageKey,messageEn,message,cause);
    }
}
