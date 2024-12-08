package org.severinu.demoapi.api.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.severinu.demoapi.api.interceptor.IHeadersService;
import org.springframework.stereotype.Service;

import static org.severinu.demoapi.api.constants.DemoApiConstants.SCHEMA_HEADER;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final IHeadersService headersService;

    public void doSomething() {
        log.info("MessageService is doing something");
        log.info("MessageService get schema header returns: {}", headersService.get(SCHEMA_HEADER));
    }

    public String getSchemaHeader() {
        return headersService.get(SCHEMA_HEADER);
    }

}
