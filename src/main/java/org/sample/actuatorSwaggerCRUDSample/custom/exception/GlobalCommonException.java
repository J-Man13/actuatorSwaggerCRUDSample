package org.sample.actuatorSwaggerCRUDSample.custom.exception;

import org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.message.IMultiLanguageComponent;
import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDescriptor;

public class GlobalCommonException extends RuntimeException {
    private final ErrorDescriptor errorDescriptor;
    private final String messageKey;
    private static IMultiLanguageComponent MULTI_LANGUAGE_COMPONENT;

    public GlobalCommonException(String messageKey, Throwable cause){
        super(
                String.format(
                        MULTI_LANGUAGE_COMPONENT.getMessageByKey(messageKey,"en"),
                        cause.getMessage()),
                cause
        );

        this.messageKey=messageKey;
        this.errorDescriptor = new ErrorDescriptor(
                this.getStackTrace()[0].getClassName(),
                messageKey,
                MULTI_LANGUAGE_COMPONENT.getMessageByKey(messageKey),
                this.getClass().getCanonicalName()
        );
    }

    public GlobalCommonException(String messageKey,String messageEn, String message) {
        super(messageEn);
        this.messageKey=messageKey;
        this.errorDescriptor = new ErrorDescriptor(
                this.getStackTrace()[0].getClassName(),
                messageKey,
                message,
                this.getClass().getCanonicalName()
        );
    }

    public GlobalCommonException(String messageKey,String messageEn, String message, Throwable cause) {
        super(messageEn,cause);
        this.messageKey=messageKey;
        this.errorDescriptor = new ErrorDescriptor(
                this.getStackTrace()[0].getClassName(),
                messageKey,
                message,
                this.getClass().getCanonicalName()
        );
    }

    public ErrorDescriptor getErrorDescriptor() {
        return errorDescriptor;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public static IMultiLanguageComponent getMultiLanguageComponent() {
        return MULTI_LANGUAGE_COMPONENT;
    }

    public static void setMultiLanguageComponent(IMultiLanguageComponent multiLanguageComponent) {
        MULTI_LANGUAGE_COMPONENT = multiLanguageComponent;
    }
}
