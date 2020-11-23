package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserInvalidCredentialsException extends GlobalCommonException {
    public CrmUserInvalidCredentialsException(String messageKey,Throwable throwable){
        super(
                messageKey,
                getMultiLanguageComponent().getMessageByKey(messageKey,"en"),
                getMultiLanguageComponent().getMessageByKey(messageKey),
                throwable
        );
    }
}
