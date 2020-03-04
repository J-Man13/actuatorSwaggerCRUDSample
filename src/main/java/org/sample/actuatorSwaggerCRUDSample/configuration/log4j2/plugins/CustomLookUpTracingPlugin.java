package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.plugins;

import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.springframework.util.StringUtils;

@Plugin(name = "ctlu", category = StrLookup.CATEGORY)
public class CustomLookUpTracingPlugin implements  StrLookup  {
    @Override
    public String lookup(String key) {
        return key;
    }

    @Override
    public String lookup(LogEvent event, String key) {
        if (key.equals("source.class.method"))
            return event.getSource().getClassName() + "." + event.getSource().getMethodName() + "()";
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
        else
            return null;
    }
}
