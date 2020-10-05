package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.logging.log4j.Logger;


import java.util.Map;

public class CommonLogger {
    private final Logger LOGGER;

    public CommonLogger(Logger logger){
        this.LOGGER = logger;
    }

    public void trace(String logCauseDescription){
        LOGGER.trace(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller())));
    }

    public void trace(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.trace(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller())));
    }

    public void trace(String logCauseDescription, Map<String,String> logMap){
        LOGGER.trace(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller())));
    }

    public void debug(String logCauseDescription){
        LOGGER.debug(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller())));
    }

    public void debug(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.debug(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller())));
    }

    public void debug(String logCauseDescription, Map<String,String> logMap) {
        LOGGER.debug(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller())));
    }

    public void info(String logCauseDescription){
        LOGGER.info(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller())));
    }

    public void info(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.info(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller())));
    }

    public void info(String logCauseDescription, Map<String,String> logMap){
        LOGGER.info(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller())));
    }

    public void warn(String logCauseDescription){
        LOGGER.warn(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller())));
    }

    public void warn(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.warn(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller())));
    }

    public void warn(String logCauseDescription, Map<String,String> logMap){
        LOGGER.warn(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller())));
    }

    public void error(String logCauseDescription)
    {
        LOGGER.error(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller())));
    }

    public void error(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.error(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller())));
    }

    public void error(String logCauseDescription, Map<String,String> logMap){
        LOGGER.error(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller())));
    }

    public void fatal(String logCauseDescription)
    {
        LOGGER.fatal(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller())));
    }

    public void fatal(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.fatal(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller())));
    }

    public void fatal(String logCauseDescription, Map<String,String> logMap){
        LOGGER.fatal(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller())));
    }

    private String getCaller(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        return stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName()+"() line : "+stackTraceElement.getLineNumber();
    }


}
