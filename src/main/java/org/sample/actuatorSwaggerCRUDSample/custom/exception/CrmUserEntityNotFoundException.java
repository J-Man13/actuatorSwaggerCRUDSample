package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserEntityNotFoundException extends GlobalHandledException {
    public CrmUserEntityNotFoundException(String messageKey,String message){
        super(messageKey,message);
    }
}
