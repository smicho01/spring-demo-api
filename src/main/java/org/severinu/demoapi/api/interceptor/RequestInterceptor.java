package org.severinu.demoapi.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static org.severinu.demoapi.api.constants.DemoApiConstants.*;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String[] headers = { SCHEMA_HEADER, ROLES_HEADER, VIEW_HEADER };
        for (String header : headers) {
            String headerValue = request.getHeader(header);
            if (headerValue != null) {
                MDC.put(header, headerValue);
            }
        }

        log.info("Incoming API request: {}",  request.getRequestURI());
        log.info("Response status: {}",  response.getStatus());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("This is request post-handle: {}", request.getRequestURI());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("Request after completion: {}",request.getRequestURI());
    }
}
