package org.severinu.demoapi.api.interceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
public class MapHeaderInterceptor implements  IHeadersService {

    private final Map<String, String> headers = new HashMap<>();

    @Override
    public void set(String key, String value) {
        this.headers.put(key, value);
    }

    @Override
    public String get(String key) {
        return this.headers.get(key);
    }
}
