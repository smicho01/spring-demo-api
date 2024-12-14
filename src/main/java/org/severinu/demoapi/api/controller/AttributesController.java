package org.severinu.demoapi.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.api.responses.SearchResultResponse;
import org.severinu.demoapi.api.service.FileOrchestrator;
import org.severinu.demoapi.api.service.FileSchemaInterpreter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/attributes")
@Slf4j
@RequiredArgsConstructor
public class AttributesController {

    private final FileSchemaInterpreter fileSchemaInterpreter;
    private final FileOrchestrator fileOrchestrator;

    @GetMapping
    public ResponseEntity<MappingJacksonValue> getFiles() {
        log.info("ServletRequestAttributesTestController.getFiles()");
        FilesSearchResultResponse searchResult = fileOrchestrator.getFiles();
        SearchResultResponse metadata = fileSchemaInterpreter.interpretFileMetadata(searchResult);
        MappingJacksonValue searchResultJackson = fileSchemaInterpreter.convertToJacksonValue(metadata);

        return new ResponseEntity<>(searchResultJackson, HttpStatus.OK);
    }

}
