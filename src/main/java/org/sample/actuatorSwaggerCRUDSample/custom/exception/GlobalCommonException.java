package org.sample.actuatorSwaggerCRUDSample.custom.exception;

import org.sample.actuatorSwaggerCRUDSample.model.common.dto.ErrorDesriptor;

public class GlobalCommonException extends RuntimeException {
    private final ErrorDesriptor errorDesriptor;
    private final String messageKey;

    public GlobalCommonException(String messageKey,String messageEn, String message, Throwable cause) {
        super(messageEn,cause);
        this.messageKey=messageKey;
        this.errorDesriptor = new ErrorDesriptor(this.getStackTrace()[0].getClassName(),messageKey,message,this.getClass().getCanonicalName());
    }
    public ErrorDesriptor getErrorDesriptor() {
        return errorDesriptor;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
