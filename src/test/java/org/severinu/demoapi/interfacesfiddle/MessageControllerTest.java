package org.severinu.demoapi.interfacesfiddle;

import org.junit.jupiter.api.Test;
import org.severinu.demoapi.api.controller.MessageController;
import org.severinu.demoapi.api.interceptor.IHeadersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.severinu.demoapi.api.constants.DemoApiConstants.SCHEMA_HEADER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
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
        String schemaHeaderValue = "DOCUMENTS";

        // Mock MDC behavior
        doNothing().when(headersService).set(SCHEMA_HEADER, schemaHeaderValue);
        when(headersService.get(SCHEMA_HEADER)).thenReturn(schemaHeaderValue);

        mockMvc.perform(get("/message")
                        .param("message", "Hello World")
                        .header(SCHEMA_HEADER, schemaHeaderValue)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Message sent"));

        verify(headersService).set(SCHEMA_HEADER, schemaHeaderValue);
        verify(headersService).get(SCHEMA_HEADER);
    }
}