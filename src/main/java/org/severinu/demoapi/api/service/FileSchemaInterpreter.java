package org.severinu.demoapi.api.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.responses.DocumentsSearchResultsResponse;
import org.severinu.demoapi.api.responses.MetadataResponse;
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

    public SearchResultResponse interpretFileMetadata() {

        List<String> list = new ArrayList<>();
        list.add("documentId1");
        list.add("documentId2");
        list.add("documentId3");

        return DocumentsSearchResultsResponse.builder()
                .documents(list)
                .location("Happy Location")
                .build();
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
