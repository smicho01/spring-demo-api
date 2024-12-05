package org.severinu.demoapi.api.interceptor;

public interface IHeadersService {
    void set(String key, String value);
    String get(String key);
}
