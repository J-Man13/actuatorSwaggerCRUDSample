package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserInvalidCredentialsException extends GlobalCommonException {
    public CrmUserInvalidCredentialsException(String messageKey,String messageEn, String message, Throwable cause){
        super(messageKey,messageEn,message,cause);
    }
}
