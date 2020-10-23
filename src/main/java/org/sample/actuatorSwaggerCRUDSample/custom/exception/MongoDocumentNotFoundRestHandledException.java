package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class MongoDocumentNotFoundRestHandledException extends GlobalRestHandledException {

    public MongoDocumentNotFoundRestHandledException(String messageKey, String message){
        super(messageKey,message);
    }
}
