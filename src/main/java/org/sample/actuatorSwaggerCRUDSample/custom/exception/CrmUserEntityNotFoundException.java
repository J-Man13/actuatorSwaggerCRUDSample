package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserEntityNotFoundException extends GlobalCommonException {
    public CrmUserEntityNotFoundException(String messageKey, String message, Throwable cause){
        super(messageKey,message,cause);
    }
}
