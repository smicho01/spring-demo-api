package org.severinu.demoapi.api.configuration;

import org.severinu.demoapi.api.interceptor.IHeadersService;
import org.severinu.demoapi.api.interceptor.RequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final IHeadersService headersService;

    public WebConfig(IHeadersService headersService) {
        this.headersService = headersService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestInterceptor(headersService));
    }
}
