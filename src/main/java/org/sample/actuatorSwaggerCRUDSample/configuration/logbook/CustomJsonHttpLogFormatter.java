package org.sample.actuatorSwaggerCRUDSample.configuration.logbook;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.sample.actuatorSwaggerCRUDSample.model.BodyLogginWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.zalando.logbook.HttpMessage;
import org.zalando.logbook.StructuredHttpLogFormatter;


import java.io.IOException;

import java.util.Map;
import java.util.Optional;

@Component
public class CustomJsonHttpLogFormatter implements StructuredHttpLogFormatter {
    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public CustomJsonHttpLogFormatter() {
        this.objectMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
    }

    public CustomJsonHttpLogFormatter(final ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public Optional<Object> prepareBody(final HttpMessage message) throws IOException {
        if (StringUtils.isEmpty(message.getBodyAsString()))
            return Optional.ofNullable(new BodyLogginWrapper(null,null));
        else {
            try { return Optional.ofNullable(new BodyLogginWrapper(message.getBodyAsString(),objectMapper.readTree(message.getBodyAsString())));} catch (IOException ioe){}
            try { return Optional.ofNullable(new BodyLogginWrapper(message.getBodyAsString(), xmlMapper.readTree(message.getBodyAsString())));}catch (IOException ioe){}
            return Optional.ofNullable(new BodyLogginWrapper(message.getBodyAsString()));
        }
    }

    @Override
    public String format(final Map<String, Object> content) throws IOException {
        try{return objectMapper.writeValueAsString(content);}catch (IOException ioe){return content.toString();}
    }
}
