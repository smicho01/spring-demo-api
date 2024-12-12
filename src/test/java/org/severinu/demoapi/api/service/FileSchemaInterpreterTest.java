package org.severinu.demoapi.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.severinu.demoapi.api.responses.DocumentsSearchResultsResponse;
import org.severinu.demoapi.api.view.View;
import org.severinu.demoapi.utils.Utilities;
import org.springframework.http.converter.json.MappingJacksonValue;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.times;
import static org.severinu.demoapi.api.constants.DemoApiConstants.VIEW_HEADER;

class FileSchemaInterpreterTest {


    private FileSchemaInterpreter fileSchemaInterpreter;

    @BeforeEach
    void setUp() {
        fileSchemaInterpreter = new FileSchemaInterpreter();
    }

//    @Test
//    void convertToJacksonValue_shouldRetrieveCorrectViewHeader() {
//        try (MockedStatic<Utilities> mocked = Mockito.mockStatic(Utilities.class)) {
//            DocumentsSearchResultsResponse documentsSearchResultsResponse = DocumentsSearchResultsResponse.builder()
//                    .documents(getDocumentsList())
//                    .location("locationA")
//                    .type("documentsA")
//                    .build();
//
//            mocked.when(() -> Utilities.getCustomHeader(VIEW_HEADER)).thenReturn(View.NORMAL);  // Mocking the view to "NORMAL"
//
//            MappingJacksonValue mappingJacksonValue = fileSchemaInterpreter.convertToJacksonValue(documentsSearchResultsResponse);
//            mappingJacksonValue.setSerializationView(View.Normal.class);
//
//            Object wrappedObject = mappingJacksonValue.getValue();
//
//            ObjectMapper objectMapper = new ObjectMapper();
//            String jsonResponse = objectMapper.writeValueAsString(wrappedObject);
//
//            mocked.verify(() -> Utilities.getCustomHeader(VIEW_HEADER) , times(2));
//
//            System.out.println("JSON Response: " + jsonResponse);
//            assertFalse(jsonResponse.contains("location"), "Location field should not be included in the NORMAL view.");
//        } catch (Exception e) {
//            fail("Test failed due to exception: " + e.getMessage());
//        }
//    }


    private static List<String> getDocumentsList() {
        return List.of("documentId1", "documentId2", "documentId3");
    }

}