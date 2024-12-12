package org.severinu.demoapi.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
public class Utilities {

    public static String getCustomHeader(String headerName) {
        log.info("Header in Utilities.getCustomHeader : {}", headerName);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return (String) attributes.getAttribute(headerName, ServletRequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

}
