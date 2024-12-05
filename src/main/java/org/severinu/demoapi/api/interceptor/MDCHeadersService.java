package org.severinu.demoapi.api.interceptor;

import org.slf4j.MDC;
import org.springframework.stereotype.Service;

@Service
public class MDCHeadersService implements IHeadersService {

    @Override
    public void set(String key, String value) {
        MDC.put(key, value);
    }

    @Override
    public String get(String key) {
        return MDC.get(key);
    }
}
