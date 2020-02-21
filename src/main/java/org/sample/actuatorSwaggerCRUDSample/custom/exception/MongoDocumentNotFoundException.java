package org.sample.actuatorSwaggerCRUDSample.custom.exception;

import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;

public class MongoDocumentNotFoundException extends RuntimeException{
    private ErrorDesriptor errorDesriptor;

    public MongoDocumentNotFoundException(String description){
        super(description);
        this.errorDesriptor = new ErrorDesriptor(this.getStackTrace()[0].getClassName(),description,this.getClass().getCanonicalName());
    }

    public ErrorDesriptor getErrorDesriptor() {
        return errorDesriptor;
    }

    public void setErrorDesriptor(ErrorDesriptor errorDesriptor) {
        this.errorDesriptor = errorDesriptor;
    }
}
