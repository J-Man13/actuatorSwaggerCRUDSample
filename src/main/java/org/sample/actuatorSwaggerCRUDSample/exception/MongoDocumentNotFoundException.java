package org.sample.actuatorSwaggerCRUDSample.exception;

import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;

public class MongoDocumentNotFoundException extends RuntimeException{
    private ErrorDesriptor errorDesriptor;

    public MongoDocumentNotFoundException(ErrorDesriptor errorDesriptor){
        super(errorDesriptor.getDescription());
        this.errorDesriptor = errorDesriptor;
    }

    public ErrorDesriptor getErrorDesriptor() {
        return errorDesriptor;
    }

    public void setErrorDesriptor(ErrorDesriptor errorDesriptor) {
        this.errorDesriptor = errorDesriptor;
    }
}
