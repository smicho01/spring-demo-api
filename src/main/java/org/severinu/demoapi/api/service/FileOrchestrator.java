package org.severinu.demoapi.api.service;

import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.utils.Utilities;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.severinu.demoapi.api.constants.DemoApiConstants.VIEW_HEADER;

@Slf4j
@Service
public class FileOrchestrator {

    public FilesSearchResultResponse getFiles(String roles) {

        log.info("Roles header in FileOrchestrator.getFiles(String roles) : {}", Utilities.getCustomHeader(VIEW_HEADER));

        List<String> list = getFilesIds();
        return FilesSearchResultResponse.builder()
                .files(list)
                .type("files")
                .location("Happy Location")
                .build();
    }

    public FilesSearchResultResponse getFiles() {

        log.info("Roles header in FileOrchestrator.getFiles() : {}", Utilities.getCustomHeader(VIEW_HEADER));

        List<String> list = getFilesIds();
        return FilesSearchResultResponse.builder()
                .files(list)
                .type("files")
                .location("Happy Location")
                .build();
    }

    private static List<String> getFilesIds() {
        List<String> list = new ArrayList<>();
        list.add("documentId1");
        list.add("documentId2");
        list.add("documentId3");
        return list;
    }
}
