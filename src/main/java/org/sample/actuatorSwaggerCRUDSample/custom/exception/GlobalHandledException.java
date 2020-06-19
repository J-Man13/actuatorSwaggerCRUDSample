package org.sample.actuatorSwaggerCRUDSample.custom.exception;

import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;

public class GlobalHandledException extends RuntimeException {
    private ErrorDesriptor errorDesriptor;

    public GlobalHandledException(String description) {
        super(description);
        this.errorDesriptor = new ErrorDesriptor(this.getStackTrace()[0].getClassName(),description,this.getClass().getCanonicalName());
    }

    public ErrorDesriptor getErrorDesriptor() {
        return errorDesriptor;
    }
}
