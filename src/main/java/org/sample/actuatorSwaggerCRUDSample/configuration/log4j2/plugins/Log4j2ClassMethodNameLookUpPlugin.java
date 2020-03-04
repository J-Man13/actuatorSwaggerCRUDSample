package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

@Plugin(name = "ccmlu", category = StrLookup.CATEGORY)
public class Log4j2ClassMethodNameLookUpPlugin implements  StrLookup  {
    @Override
    public String lookup(String key) {
        return key;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        if (key.equals("source.class.method"))
            return event.getSource().getClassName() + "." + event.getSource().getMethodName() + "()";
        else
            return null;
    }
}
