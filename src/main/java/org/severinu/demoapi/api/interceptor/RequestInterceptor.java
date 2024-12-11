package org.severinu.demoapi.api.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static org.severinu.demoapi.api.constants.DemoApiConstants.*;

@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    private final IHeadersService headersService;

    public RequestInterceptor(IHeadersService headersService) {
        this.headersService = headersService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // List of potential headers to store
        String[] headers = { SCHEMA_HEADER, ROLES_HEADER, VIEW_HEADER };

        // Solution 1 - Store headers in MDC
        for (String header : headers) {
            String headerValue = request.getHeader(header);
            if (headerValue != null) {
                log.info("Setting MDC value with [key:] {} [value:] {}", header, headerValue);
                MDC.put(header, headerValue);
                headersService.set(header, headerValue);
            }
        }

        // Solution 2 - Store headers in RequestAttributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        for (String header : headers) {
            String headerValue = request.getHeader(header);
            if (headerValue != null) {
                log.info("Store header: {} with value: {} in ServletRequestAttributes", header, headerValue);
                if(attributes != null) {
                    attributes.setAttribute(header, headerValue, ServletRequestAttributes.SCOPE_REQUEST);
                }
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
        MDC.clear();
        log.info("MDC cleared");
    }
}
