package org.sample.actuatorSwaggerCRUDSample.configuration.logging.util;


import org.apache.logging.log4j.Logger;


import java.util.Map;

public class CommonLogger {
    private final Logger LOGGER;

    public CommonLogger(Logger logger){
        this.LOGGER = logger;
    }

    public void trace(String logCauseDescription){
        LOGGER.trace(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller(),"TRACE")));
    }

    public void trace(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.trace(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller(),"TRACE")));
    }

    public void trace(String logCauseDescription, Map<String,String> logMap){
        LOGGER.trace(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller(),"TRACE")));
    }

    public void debug(String logCauseDescription){
        LOGGER.debug(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller(),"DEBUG")));
    }

    public void debug(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.debug(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller(),"DEBUG")));
    }

    public void debug(String logCauseDescription, Map<String,String> logMap) {
        LOGGER.debug(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller(),"DEBUG")));
    }

    public void info(String logCauseDescription){
        LOGGER.info(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller(),"INFO")));
    }

    public void info(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.info(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller(),"INFO")));
    }

    public void info(String logCauseDescription, Map<String,String> logMap){
        LOGGER.info(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller(),"INFO")));
    }

    public void warn(String logCauseDescription){
        LOGGER.warn(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller(),"WARN")));
    }

    public void warn(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.warn(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller(),"WARN")));
    }

    public void warn(String logCauseDescription, Map<String,String> logMap){
        LOGGER.warn(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller(),"WARN")));
    }

    public void error(String logCauseDescription)
    {
        LOGGER.error(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller(),"ERROR")));
    }

    public void error(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.error(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller(),"ERROR")));
    }

    public void error(String logCauseDescription, Map<String,String> logMap){
        LOGGER.error(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller(),"ERROR")));
    }

    public void fatal(String logCauseDescription)
    {
        LOGGER.fatal(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,getCaller(),"FATAL")));
    }

    public void fatal(String logCauseDescription, String loggableObjectKey, Object data){
        LOGGER.fatal(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,loggableObjectKey,data,getCaller(),"FATAL")));
    }

    public void fatal(String logCauseDescription, Map<String,String> logMap){
        LOGGER.fatal(new CommonLoggableJsonMessage(new CommonLoggingObject(logCauseDescription,logMap,getCaller(),"FATAL")));
    }

    private String getCaller(){
        StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
        return stackTraceElement.getClassName()+"."+stackTraceElement.getMethodName()+"() line : "+stackTraceElement.getLineNumber();
    }


}
