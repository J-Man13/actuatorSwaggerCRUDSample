package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserInvalidCredentialsException extends GlobalCommonException {
    public CrmUserInvalidCredentialsException(String messageKey, String message, Throwable cause){
        super(messageKey,message,cause);
    }
}
