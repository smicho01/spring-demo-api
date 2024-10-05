package org.severinu.demoapi.interfacesfiddle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    void testSendMessage() throws Exception {
        // Define behavior for your mocks
        doNothing().when(emailSenderMock).send(anyString());
        doNothing().when(smsSenderMock).send(anyString());

        // Perform a GET request to the /message endpoint
        mockMvc.perform(get("/message")
                        .param("message", "Hello World"))
                .andExpect(status().isOk());

        // Verify interactions
        verify(emailSenderMock).send("Hello World");
        verify(smsSenderMock).send("Hello World");
    }
}