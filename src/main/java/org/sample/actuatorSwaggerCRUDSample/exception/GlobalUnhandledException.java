package org.sample.actuatorSwaggerCRUDSample.exception;

import org.sample.actuatorSwaggerCRUDSample.model.ErrorDesriptor;

public class GlobalUnhandledException extends RuntimeException {
    private ErrorDesriptor errorDesriptor;

    public GlobalUnhandledException(ErrorDesriptor errorDesriptor) {
        super(errorDesriptor.getDescription());
        this.errorDesriptor = errorDesriptor;
    }

    public ErrorDesriptor getErrorDesriptor() {
        return errorDesriptor;
    }
}
