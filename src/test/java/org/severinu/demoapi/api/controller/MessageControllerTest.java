package org.severinu.demoapi.api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.severinu.demoapi.api.interceptor.IHeadersService;
import org.severinu.demoapi.api.interceptor.RequestInterceptor;
import org.severinu.demoapi.api.responses.DocumentsSearchResultsResponse;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.api.service.FileOrchestrator;
import org.severinu.demoapi.api.service.FileSchemaInterpreter;
import org.severinu.demoapi.api.service.MessageService;
import org.severinu.demoapi.api.view.View;
import org.severinu.demoapi.interfacesfiddle.EmailNotification;
import org.severinu.demoapi.interfacesfiddle.INotificationSender;
import org.severinu.demoapi.interfacesfiddle.SmsNotification;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.severinu.demoapi.api.constants.DemoApiConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
@Import(RequestInterceptor.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    @EmailNotification
    private INotificationSender emailSenderMock;

    @MockBean
    @SmsNotification
    private INotificationSender smsSenderMock;

    @MockBean
    private IHeadersService headersService;

    @MockBean
    private MessageService messageService;

    @MockBean
    private FileSchemaInterpreter fileSchemaInterpreter;

    @MockBean
    private FileOrchestrator fileOrchestrator;

    @BeforeEach
    public void setUp() {
        when(headersService.get(SCHEMA_HEADER)).thenReturn("FILES");
        when(headersService.get(VIEW_HEADER)).thenReturn("EXTENDED");
        when(headersService.get(ROLES_HEADER)).thenReturn("role1");
    }


    @Test
    void testSendMessage() throws Exception {
        doNothing().when(emailSenderMock).send(anyString());
        doNothing().when(smsSenderMock).send(anyString());

        mockMvc.perform(get("/message")
                        .param("message", "Hello World"))
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent"));

        verify(emailSenderMock).send("Hello World");
        verify(smsSenderMock).send("Hello World");
    }

    @Test
    void testSetHeaders() throws Exception {
        try (MockedStatic<MDC> mockedMDC = mockStatic(MDC.class)) {
            String schemaHeaderValue = "DOCUMENTS";

            mockedMDC.when(() -> MDC.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);

            mockMvc.perform(MockMvcRequestBuilders.get("/message")
                            .param("message", "Hello World")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                    )
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string("Message sent"))
                    .andDo(result -> {
                        assertEquals(schemaHeaderValue, MDC.get(SCHEMA_HEADER));
                    });

            mockedMDC.verify(() -> MDC.put(SCHEMA_HEADER, schemaHeaderValue));

            String schemaHeaderFromMDC = MDC.get(SCHEMA_HEADER);
            assertEquals(schemaHeaderValue, schemaHeaderFromMDC); // test again because why not :)

            verify(headersService).set(SCHEMA_HEADER, schemaHeaderValue);
            verify(headersService).get(SCHEMA_HEADER);
        }
    }

    @Test
    void testGetHeadersInMessageService() throws Exception {
        try (MockedStatic<MDC> mockedMDC = mockStatic(MDC.class)) {
            String schemaHeaderValue = "FILES";

            // Mock MDC behavior
            doNothing().when(headersService).set(SCHEMA_HEADER, schemaHeaderValue);
            when(headersService.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);
            doNothing().when(messageService).doSomething();
            when(messageService.getSchemaHeader()).thenReturn(schemaHeaderValue);

            mockedMDC.when(() -> MDC.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);

            mockMvc.perform(get("/message")
                            .param("message", "Hello World")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().string("Message sent"))
                    .andDo(result -> {
                        assertEquals(schemaHeaderValue, MDC.get(SCHEMA_HEADER));
                    });

            mockedMDC.verify(() -> MDC.put(SCHEMA_HEADER, schemaHeaderValue));

            verify(headersService).set(SCHEMA_HEADER, schemaHeaderValue);
            assertEquals(schemaHeaderValue, messageService.getSchemaHeader());
        }
    }

    @Test
    void testGetAllMessagesWithLoadsOfDependencies_shouldReturnDocuments() throws Exception {
        try (MockedStatic<MDC> mockedMDC = mockStatic(MDC.class)) {
            String schemaHeaderValue = "DOCUMENTS";

            mockedMDC.when(() -> MDC.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);
            mockedMDC.when(() -> MDC.get(VIEW_HEADER)).thenReturn("NORMAL");

            FilesSearchResultResponse searchResultResponse = FilesSearchResultResponse.builder()
                    .files(getDocumentsList())
                    .type("files")
                    .location("location")
                    .build();

            DocumentsSearchResultsResponse documentsSearchResultsResponse = DocumentsSearchResultsResponse.builder()
                    .documents(getDocumentsList())
                    .location("location")
                    .type("documents")
                    .build();

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(documentsSearchResultsResponse);
            //mappingJacksonValue.setSerializationView(View.EXTENDED.getClass());

            when(fileOrchestrator.getFiles(anyString()))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.interpretFileMetadata(searchResultResponse, schemaHeaderValue))
                    .thenReturn(documentsSearchResultsResponse);
            when(fileSchemaInterpreter.convertToJacksonValue(documentsSearchResultsResponse, "EXTENDED"))
                    .thenReturn(mappingJacksonValue);
            when(headersService.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);



            mockMvc.perform(get("/message/dependencies")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type", is("documents")))
                    .andExpect(jsonPath("$.location", is("location")))
                    .andExpect(jsonPath("$.documents", hasSize(3)))
                    .andExpect(jsonPath("$.documents[0]", is("documentId1")))
                    .andExpect(jsonPath("$.documents[1]", is("documentId2")))
                    .andExpect(jsonPath("$.documents[2]", is("documentId3")))
                    .andDo(result -> {
                        assertEquals(schemaHeaderValue, MDC.get(SCHEMA_HEADER));
                    });
        }
    }

    @Test
    void testGetAllMessagesWithLoadsOfDependencies_shouldReturnFiles() throws Exception {
        try (MockedStatic<MDC> mockedMDC = mockStatic(MDC.class)) {
            String schemaHeaderValue = "FILES";

            FilesSearchResultResponse searchResultResponse = FilesSearchResultResponse.builder()
                    .files(getDocumentsList())
                    .type("files")
                    .location("location")
                    .build();

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(searchResultResponse);

            mockedMDC.when(() -> MDC.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);

            when(fileOrchestrator.getFiles(anyString()))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.interpretFileMetadata(searchResultResponse, schemaHeaderValue))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.convertToJacksonValue(searchResultResponse, "EXTENDED"))
                    .thenReturn(mappingJacksonValue);
            when(headersService.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);

            mockMvc.perform(get("/message/dependencies")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type", is("files")))
                    .andExpect(jsonPath("$.location", is("location")))
                    .andExpect(jsonPath("$.files", hasSize(3)))
                    .andExpect(jsonPath("$.files[0]", is("documentId1")))
                    .andExpect(jsonPath("$.files[1]", is("documentId2")))
                    .andExpect(jsonPath("$.files[2]", is("documentId3")))
                    .andDo(result -> {
                        assertEquals(schemaHeaderValue, MDC.get(SCHEMA_HEADER));
                        mockedMDC.verify(() -> MDC.put(SCHEMA_HEADER, schemaHeaderValue));
                    });
        }
    }

    @Test
    void testNormalViewHeader() throws Exception {
        try (MockedStatic<MDC> mockedMDC = mockStatic(MDC.class)) {
            String schemaHeaderValue = "FILES";
            String viewHeaderValue = "NORMAL";

            FilesSearchResultResponse searchResultResponse = FilesSearchResultResponse.builder()
                    .files(getDocumentsList())
                    .type("files")
                    .location("location")
                    .build();

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(searchResultResponse);
            mappingJacksonValue.setSerializationView(View.Normal.class);

            when(fileOrchestrator.getFiles(anyString()))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.interpretFileMetadata(searchResultResponse, schemaHeaderValue))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.convertToJacksonValue(searchResultResponse, viewHeaderValue))
                    .thenReturn(mappingJacksonValue);

            when(headersService.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);
            when(headersService.get(VIEW_HEADER)).thenReturn(viewHeaderValue);

            mockedMDC.when(() -> MDC.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);
            mockedMDC.when(() -> MDC.get(VIEW_HEADER)).thenReturn(viewHeaderValue);

            mockMvc.perform(get("/message/dependencies")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                            .header(VIEW_HEADER, viewHeaderValue)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type", is("files")))
                    .andExpect(jsonPath("$.files", hasSize(3)))
                    .andDo(result -> {
                        assertEquals(schemaHeaderValue, MDC.get(SCHEMA_HEADER));
                        assertEquals(viewHeaderValue, MDC.get(VIEW_HEADER));
                    });
            mockedMDC.verify(() -> MDC.put(SCHEMA_HEADER, schemaHeaderValue));
            mockedMDC.verify(() -> MDC.put(VIEW_HEADER, viewHeaderValue));
        }
    }

    @Test
    void testExtendedViewHeader() throws Exception {
        try (MockedStatic<MDC> mockedMDC = mockStatic(MDC.class)) {
            String schemaHeaderValue = "FILES";
            String viewHeaderValue = "NORMAL";

            FilesSearchResultResponse searchResultResponse = FilesSearchResultResponse.builder()
                    .files(getDocumentsList())
                    .type("files")
                    .location("location")
                    .build();

            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(searchResultResponse);
            mappingJacksonValue.setSerializationView(View.Extended.class);

            mockedMDC.when(() -> MDC.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);
            mockedMDC.when(() -> MDC.get(VIEW_HEADER)).thenReturn(viewHeaderValue);

            when(fileOrchestrator.getFiles(anyString()))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.interpretFileMetadata(searchResultResponse, schemaHeaderValue))
                    .thenReturn(searchResultResponse);
            when(fileSchemaInterpreter.convertToJacksonValue(searchResultResponse, viewHeaderValue))
                    .thenReturn(mappingJacksonValue);

            when(headersService.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);
            when(headersService.get(VIEW_HEADER)).thenReturn(viewHeaderValue);

            mockMvc.perform(get("/message/dependencies")
                            .header(SCHEMA_HEADER, schemaHeaderValue)
                            .header(VIEW_HEADER, viewHeaderValue)
                    )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.type", is("files")))
                    .andExpect(jsonPath("$.location", is("location")))
                    .andExpect(jsonPath("$.files", hasSize(3)))
                    .andDo(result -> {
                        assertEquals(schemaHeaderValue, MDC.get(SCHEMA_HEADER));
                        assertEquals(viewHeaderValue, MDC.get(VIEW_HEADER));
                        mockedMDC.verify(() -> MDC.put(SCHEMA_HEADER, schemaHeaderValue));
                        mockedMDC.verify(() -> MDC.put(VIEW_HEADER, viewHeaderValue));
                    });
        }
    }

    private static List<String> getDocumentsList() {
        List<String> list = new ArrayList<>();
        list.add("documentId1");
        list.add("documentId2");
        list.add("documentId3");
        return list;
    }

}