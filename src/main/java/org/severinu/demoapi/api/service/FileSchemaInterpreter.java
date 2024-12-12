package org.severinu.demoapi.api.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.responses.DocumentsSearchResultsResponse;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.api.responses.SearchResultResponse;
import org.severinu.demoapi.api.view.View;
import org.severinu.demoapi.utils.Utilities;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.severinu.demoapi.api.constants.DemoApiConstants.SCHEMA_HEADER;
import static org.severinu.demoapi.api.constants.DemoApiConstants.VIEW_HEADER;
import static org.severinu.demoapi.api.view.View.EXTENDED;
import static org.severinu.demoapi.api.view.View.NORMAL;

@Slf4j
@Service
@NoArgsConstructor
public class FileSchemaInterpreter {

    public SearchResultResponse interpretFileMetadata(FilesSearchResultResponse filesSearchResultResponse,
                                                      String schema) {
        if(logOutAndReturnSchema(schema).equalsIgnoreCase("DOCUMENTS")) {
            List<String> list = filesSearchResultResponse.getFiles();
            return DocumentsSearchResultsResponse.builder()
                    .documents(list)
                    .location("Happy Location")
                    .type("documents")
                    .build();
        }

        return filesSearchResultResponse;
    }

    public SearchResultResponse interpretFileMetadata(FilesSearchResultResponse filesSearchResultResponse) {
        return interpretFileMetadata(filesSearchResultResponse, Utilities.getCustomHeader(SCHEMA_HEADER));
    }


    /*
        Pointless method, but used in test to verify if it was called.
     */
    public String logOutAndReturnSchema(String schema) {
        log.warn("interpretFileMetadata schema param: {}", schema);
        return schema;
    }

    public MappingJacksonValue convertToJacksonValue(Object object, String view) {
        MappingJacksonValue objectAsJackson = new MappingJacksonValue(object);
        if ( view != null && view.equalsIgnoreCase(EXTENDED)){
            objectAsJackson.setSerializationView(View.Extended.class);
            log.info("Serialization view: EXTENDED");
        } else {
            objectAsJackson.setSerializationView(View.Normal.class);
            log.info("Serialization view: NORMAL");
        }
        return objectAsJackson;
    }

    public MappingJacksonValue convertToJacksonValue(Object object) {
        log.info("Header in convertToJacksonValue(Object object) : {}", Utilities.getCustomHeader(VIEW_HEADER));
        return convertToJacksonValue(object,  Utilities.getCustomHeader(VIEW_HEADER));
    }
}
