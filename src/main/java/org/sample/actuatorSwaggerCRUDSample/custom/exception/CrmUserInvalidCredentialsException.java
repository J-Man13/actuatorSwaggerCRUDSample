package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserInvalidCredentialsException extends GlobalHandledException {
    public CrmUserInvalidCredentialsException(String messageKey, String message){
        super(messageKey,message);
    }
}
