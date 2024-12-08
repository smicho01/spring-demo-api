package org.severinu.demoapi.api.controller;

import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.interceptor.IHeadersService;
import org.severinu.demoapi.api.responses.FilesSearchResultResponse;
import org.severinu.demoapi.api.responses.SearchResultResponse;
import org.severinu.demoapi.api.service.FileOrchestrator;
import org.severinu.demoapi.api.service.FileSchemaInterpreter;
import org.severinu.demoapi.api.service.MessageService;
import org.severinu.demoapi.interfacesfiddle.EmailNotification;
import org.severinu.demoapi.interfacesfiddle.INotificationSender;
import org.severinu.demoapi.interfacesfiddle.SmsNotification;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.severinu.demoapi.api.constants.DemoApiConstants.*;

@Slf4j
@RestController
@RequestMapping("/message")
public class MessageController {

    private final INotificationSender emailSender;
    private final INotificationSender smsSender;

    private final MessageService messageService;
    private final IHeadersService headersService;

    private final FileSchemaInterpreter fileSchemaInterpreter;
    private final FileOrchestrator fileOrchestrator;

    public MessageController(@EmailNotification INotificationSender emailSender,
                             @SmsNotification INotificationSender smsSender, MessageService messageService, IHeadersService headersService, FileSchemaInterpreter fileSchemaInterpreter, FileOrchestrator fileOrchestrator) {
        this.emailSender = emailSender;
        this.smsSender = smsSender;
        this.messageService = messageService;
        this.headersService = headersService;
        this.fileSchemaInterpreter = fileSchemaInterpreter;
        this.fileOrchestrator = fileOrchestrator;
    }

    @GetMapping
    public ResponseEntity<String> sendAllMessages(@PathParam("message") String message) {

            log.info("MessageController sendAllMessages()");
            log.info("[GET] /messages");
            emailSender.send(message);
            smsSender.send(message);

            log.info("Schema header sent with this request is: {}", headersService.get(SCHEMA_HEADER));
            messageService.doSomething();
            messageService.getSchemaHeader();

            return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }

    @GetMapping("/dependencies")
    public ResponseEntity<MappingJacksonValue> getAllMessagesWithLoadsOfDependencies(@RequestHeader MultiValueMap<String, String> headers) {
        log.info("Headers: {}", headers);

        FilesSearchResultResponse searchResult = fileOrchestrator.getFiles(headersService.get(ROLES_HEADER));
        SearchResultResponse metadata = fileSchemaInterpreter.interpretFileMetadata(searchResult, headersService.get(SCHEMA_HEADER));
        MappingJacksonValue searchResultJackson = fileSchemaInterpreter.convertToJacksonValue(metadata, headersService.get(VIEW_HEADER));

        return new ResponseEntity<>(searchResultJackson, HttpStatus.OK);
    }
}
