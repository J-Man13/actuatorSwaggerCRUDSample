package org.sample.actuatorSwaggerCRUDSample.custom.exception;


public class MongoDocumentNotFoundException extends GlobalCommonException {
    private final String attributeValue;

    public MongoDocumentNotFoundException(String messageKey,String attributeValue){
        super(
                messageKey,
                String.format(getMultiLanguageComponent().getMessageByKey(messageKey,"en"),attributeValue),
                String.format(getMultiLanguageComponent().getMessageByKey(messageKey),attributeValue)
        );
        this.attributeValue = attributeValue;
    }

    public String getAttributeValue() {
        return attributeValue;
    }
}
