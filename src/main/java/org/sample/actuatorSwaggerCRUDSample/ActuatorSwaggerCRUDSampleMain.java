package org.sample.actuatorSwaggerCRUDSample;


import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLoggingObject;
import org.sample.actuatorSwaggerCRUDSample.configuration.logging.util.CommonLoggingPropertiesConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class ActuatorSwaggerCRUDSampleMain {
    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(ActuatorSwaggerCRUDSampleMain.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setCommonLoggingPropertiesConfigAndInitHostNameAndAdressAfterStartUp() throws UnknownHostException {
        CommonLoggingPropertiesConfig commonLoggingPropertiesConfig = context.getAutowireCapableBeanFactory().getBean(CommonLoggingPropertiesConfig.class);
        commonLoggingPropertiesConfig.setHostNameAndHostAddress(InetAddress.getLocalHost().getHostName(),InetAddress.getLocalHost().getHostAddress());
        CommonLoggingObject.commonLoggingPropertiesConfig = commonLoggingPropertiesConfig;
    }
}
