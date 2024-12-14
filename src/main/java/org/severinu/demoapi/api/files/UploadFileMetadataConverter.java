package org.severinu.demoapi.api.files;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class UploadFileMetadataConverter implements Converter<String, UploadFileMetadata> {


    private final ObjectMapper objectMapper;

    @Override
    public UploadFileMetadata convert(String source) {
        try {
            return objectMapper.readValue(source, UploadFileMetadata.class);
        } catch (IOException e) {
            log.error("Error code: {}: Error during UploadFileMetadata conversion:", "INVALID REQUEST JSON" );
            throw new RuntimeException("Error during UploadFileMetadata conversion", e);
        }
    }
}
