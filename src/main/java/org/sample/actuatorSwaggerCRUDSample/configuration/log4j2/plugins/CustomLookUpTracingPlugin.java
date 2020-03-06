package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.plugins;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.util.StringUtils;

import java.util.UUID;

@Plugin(name = "ctlu", category = StrLookup.CATEGORY)
public class CustomLookUpTracingPlugin implements StrLookup {
    @Override
    public String lookup(String key) {
        return key;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        if (key.equals("source.class.method"))
            return event.getSource().getClassName() + "." + event.getSource().getMethodName() + "(), line: " +event.getSource().getLineNumber();
        else if(key.equals("trace.order")){
            int traceOrderInt;
            String traceOrderString = ThreadContext.get("trace.order");
            if (StringUtils.isEmpty(traceOrderString))
                traceOrderInt = 1;
            else
                traceOrderInt = Integer.parseInt(traceOrderString)+1;
            ThreadContext.put("trace.order",String.valueOf(traceOrderInt));
            return String.valueOf(traceOrderInt);
        }
        else if(key.equals("request.identifier")){
            String requestIdentifier = ThreadContext.get("request.identifier");
            if (StringUtils.isEmpty(requestIdentifier)) {
                requestIdentifier = UUID.randomUUID().toString();
                ThreadContext.put("request.identifier",requestIdentifier);
            }
            return requestIdentifier;
        }
        else
            return null;
    }
}