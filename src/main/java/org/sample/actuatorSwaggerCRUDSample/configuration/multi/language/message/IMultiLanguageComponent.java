package org.sample.actuatorSwaggerCRUDSample.configuration.multi.language.message;

public interface IMultiLanguageComponent {
    String getMessageByKey(String key);
    String getMessageByKey(String key, String languageCode);
}
