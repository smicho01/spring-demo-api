package org.severinu.demoapi.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Utilities {

    public static String getCustomHeader(String headerName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return (String) attributes.getAttribute(headerName, ServletRequestAttributes.SCOPE_REQUEST);
        }
        return null;
    }

}
