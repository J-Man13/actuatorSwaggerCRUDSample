package org.sample.actuatorSwaggerCRUDSample.configuration.multi.language;

public interface IMultiLanguageComponent {
    String getMessageByKey(String key);
    String getMessageByKey(String key, String languageCode);
}
