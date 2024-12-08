package org.severinu.demoapi.api.service;

import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FileOrchestrator {
    public FilesSearchResultResponse getFiles(String roles) {

        List<String> list = new ArrayList<>();
        list.add("documentId1");
        list.add("documentId2");
        list.add("documentId3");

        return FilesSearchResultResponse.builder()
                .files(list)
                .type("files")
                .location("Happy Location")
                .build();
    }
}
