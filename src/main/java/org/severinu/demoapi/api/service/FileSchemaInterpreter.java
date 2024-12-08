package org.severinu.demoapi.api.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.responses.DocumentsSearchResultsResponse;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.api.responses.SearchResultResponse;
import org.severinu.demoapi.api.view.View;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.severinu.demoapi.api.view.View.EXTENDED;

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


    /*
        Pointless method, but used in test to verify if it was called.
     */
    public String logOutAndReturnSchema(String schema) {
        log.warn("interpretFileMetadata schema param: {}", schema);
        return schema;
    }

    public MappingJacksonValue convertToJacksonValue(Object object, String view) {
        MappingJacksonValue objectAsJackson = new MappingJacksonValue(object);
        if ( view != null && view.equals(EXTENDED)){
            objectAsJackson.setSerializationView(View.Extended.class);
            log.info("EXTENDED view");
        } else {
            objectAsJackson.setSerializationView(View.Normal.class);
            log.info("NORMAL view");
        }
        return objectAsJackson;
    }
}
