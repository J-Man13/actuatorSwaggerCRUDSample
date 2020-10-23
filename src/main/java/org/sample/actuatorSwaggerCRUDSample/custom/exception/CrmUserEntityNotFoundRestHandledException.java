package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserEntityNotFoundRestHandledException extends GlobalRestHandledException {
    public CrmUserEntityNotFoundRestHandledException(String messageKey, String message){
        super(messageKey,message);
    }
}
