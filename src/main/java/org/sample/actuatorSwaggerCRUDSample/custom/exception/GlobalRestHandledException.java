package org.sample.actuatorSwaggerCRUDSample.custom.exception;

import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDesriptor;

public class GlobalRestHandledException extends RuntimeException {
    private ErrorDesriptor errorDesriptor;

    public GlobalRestHandledException(String messageKey, String message) {
        super(messageKey);
        this.errorDesriptor = new ErrorDesriptor(this.getStackTrace()[0].getClassName(),messageKey,message,this.getClass().getCanonicalName());
    }

    public ErrorDesriptor getErrorDesriptor() {
        return errorDesriptor;
    }
}
