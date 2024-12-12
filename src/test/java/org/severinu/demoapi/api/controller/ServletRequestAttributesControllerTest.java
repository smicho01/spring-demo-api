package org.severinu.demoapi.api.controller;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.severinu.demoapi.api.interceptor.RequestInterceptor;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.api.service.FileOrchestrator;
import org.severinu.demoapi.api.service.FileSchemaInterpreter;
import org.severinu.demoapi.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.severinu.demoapi.api.constants.DemoApiConstants.SCHEMA_HEADER;
import static org.severinu.demoapi.api.constants.DemoApiConstants.VIEW_HEADER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(RequestInterceptor.class)
class ServletRequestAttributesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileSchemaInterpreter fileSchemaInterpreter;

    @MockBean
    private FileOrchestrator fileOrchestrator;

    private static List<String> getDocumentsList() {
        List<String> list = new ArrayList<>();
        list.add("documentId1");
        list.add("documentId2");
        list.add("documentId3");
        return list;
    }

    @Test
    void a() throws Exception {
        try (MockedStatic<Utilities> mocked = Mockito.mockStatic(Utilities.class)) {

            String schemaHeaderValue = "FILES";
            String viewHeaderValue = "NORMAL";

            FilesSearchResultResponse searchResultResponse = FilesSearchResultResponse.builder()
                    .files(getDocumentsList())
                    .type("files")
                    .location("location")
                    .build();

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(searchResultResponse);

            MockHttpServletRequest request = new MockHttpServletRequest();
            request.addHeader(SCHEMA_HEADER, schemaHeaderValue);
            request.addHeader(VIEW_HEADER, viewHeaderValue);

            RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

            mocked.when(() -> Utilities.getCustomHeader(VIEW_HEADER)).thenReturn(viewHeaderValue);

            when(fileOrchestrator.getFiles())
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.interpretFileMetadata(searchResultResponse))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.convertToJacksonValue(searchResultResponse))
                    .thenReturn(mappingJacksonValue);

            mockMvc.perform(get("/attributes")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                            .header(VIEW_HEADER, viewHeaderValue)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type", is("files")))
                    .andExpect(jsonPath("$.files", hasSize(3)))
                    .andExpect(jsonPath("$.location", is("location")))
                    .andDo(result -> {
                        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                        assertNotNull(attributes, "RequestAttributes should not be null.");

                        String schemaHeader = attributes.getRequest().getHeader(SCHEMA_HEADER);
                        String viewHeader = attributes.getRequest().getHeader(VIEW_HEADER);

                        assertEquals(schemaHeaderValue, schemaHeader, "Schema header value should match.");
                        assertEquals(viewHeaderValue, viewHeader, "View header value should match.");

                    });
        }
    }

}