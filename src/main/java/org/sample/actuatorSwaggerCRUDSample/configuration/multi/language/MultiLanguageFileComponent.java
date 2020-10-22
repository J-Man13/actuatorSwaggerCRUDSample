package org.sample.actuatorSwaggerCRUDSample.configuration.multi.language;



import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.sample.actuatorSwaggerCRUDSample.util.CommonUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;

import java.util.List;
import java.util.Map;


@Component
public class MultiLanguageFileComponent implements IMultiLanguageComponent {
    private final String DEFAULT_MESSAGE_LANGUAGE_CODE;
    private final String MULTI_LANGUAGE_FILE_LOCATION;
    private final List<String> availableMessageLanguageCodes;

    private Map<String, MultiLanguageMessage> messageCodeMessageLanguageMap;

    private final String LANGUAGE_CODE_HEADER_KEY = "language.code";

    public MultiLanguageFileComponent(final @Value("${default.message.language.code}") String defaultLanguageCode,
                                      final @Value("${multi-language-messages.file.location}") String multiLanguageFileLocation,
                                      final @Value("#{'${available.message.language.codes}'.split(',')}")List<String> availableMessageLanguageCodes) throws IOException {
        DEFAULT_MESSAGE_LANGUAGE_CODE = defaultLanguageCode;
        MULTI_LANGUAGE_FILE_LOCATION = multiLanguageFileLocation;
        messageCodeMessageLanguageMap = new ObjectMapper().readValue(ResourceUtils.getFile(MULTI_LANGUAGE_FILE_LOCATION), new TypeReference<Map<String, MultiLanguageMessage>>(){});
        this.availableMessageLanguageCodes = availableMessageLanguageCodes;
    }

    @Override
    public String getMessageByKey(String key) {
        String languageCode = CommonUtil.getHeaderValueByKey(LANGUAGE_CODE_HEADER_KEY);
        if (StringUtils.isEmpty(languageCode))
            return getMessageByKey(key,DEFAULT_MESSAGE_LANGUAGE_CODE);
        else {
            if (availableMessageLanguageCodes.stream().anyMatch(s -> s.equals(languageCode)))
                return getMessageByKey(key, languageCode);
            else
                return getMessageByKey(key, DEFAULT_MESSAGE_LANGUAGE_CODE);
        }
    }

    @Override
    public String getMessageByKey(String key, final String languageCode) {
        String switchLanguageCode = DEFAULT_MESSAGE_LANGUAGE_CODE;
        if (availableMessageLanguageCodes.stream().anyMatch(s -> s.equals(languageCode)))
            switchLanguageCode = languageCode;

        MultiLanguageMessage multiLanguageMessage = messageCodeMessageLanguageMap.get(key);
        if (multiLanguageMessage == null)
            return String.format("Unable to find multi language message by %s key",key);

        switch (switchLanguageCode) {
            case "en":
                return multiLanguageMessage.getEn();
            case "ru":
                return multiLanguageMessage.getRu();
            case "az":
                return  multiLanguageMessage.getAz();
            default:
                return String.format("Multi language message with %s key does not have support for language with %s language code, that is a technical misfit, asking to contact api owners to handle the issue of multi language message addition",key,languageCode);
        }
    }
}
