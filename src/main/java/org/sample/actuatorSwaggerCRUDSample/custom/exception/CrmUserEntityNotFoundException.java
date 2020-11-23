package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserEntityNotFoundException extends GlobalCommonException {
    public CrmUserEntityNotFoundException(String messageKey,String messageEn, String message, Throwable cause){
        super(messageKey,messageEn,message,cause);
    }
}
