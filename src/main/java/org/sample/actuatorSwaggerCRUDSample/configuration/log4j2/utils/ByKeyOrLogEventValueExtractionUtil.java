package org.sample.actuatorSwaggerCRUDSample.configuration.log4j2.utils;

import org.apache.logging.log4j.core.LogEvent;

public interface ByKeyOrLogEventValueExtractionUtil {
    String extract(LogEvent logEvent);
}