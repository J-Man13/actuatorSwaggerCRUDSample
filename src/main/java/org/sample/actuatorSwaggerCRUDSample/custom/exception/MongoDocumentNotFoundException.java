package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class MongoDocumentNotFoundException extends GlobalHandledException {

    public MongoDocumentNotFoundException(String description){
        super(description);
    }
}
