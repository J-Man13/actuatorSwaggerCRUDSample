package org.sample.actuatorSwaggerCRUDSample.custom.exception;

public class CrmUserEntityNotFoundException extends GlobalCommonException {
    public CrmUserEntityNotFoundException(String messageKey,String attributeValue){
        super(
                messageKey,
                String.format(getMultiLanguageComponent().getMessageByKey(messageKey,"en"),attributeValue),
                String.format(getMultiLanguageComponent().getMessageByKey(messageKey),attributeValue)
        );
    }
}
