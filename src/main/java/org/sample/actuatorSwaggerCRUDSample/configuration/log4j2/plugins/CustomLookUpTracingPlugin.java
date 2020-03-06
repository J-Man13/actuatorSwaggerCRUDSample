package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.plugins;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;
import org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.utils.ByKeyOrLogEventValueExtractionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Map;
import java.util.Objects;

@Plugin(name = "ctlu", category = StrLookup.CATEGORY)
public class CustomLookUpTracingPlugin implements StrLookup {
    @Override
    public String lookup(String key) {
        return key;
    }

    @Autowired
    @Qualifier("customExtractionMap")
    private Map<String, ByKeyOrLogEventValueExtractionUtil> customExtractionMap;

    @Override
    public String lookup(LogEvent event, String key) {
        ByKeyOrLogEventValueExtractionUtil byKeyOrLogEventValueExtractionUtil = customExtractionMap.get(key);
        if (Objects.nonNull(byKeyOrLogEventValueExtractionUtil))
            return byKeyOrLogEventValueExtractionUtil.extract(event);
        else
            return null;
    }
}