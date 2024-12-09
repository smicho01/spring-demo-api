package org.severinu.demoapi.api.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;

class MapHeadersServiceTest {

    private static final String KEY = "key1";
    private static final String VALUE = "val1";

    private IHeadersService headersService;

    @BeforeEach
    void setUp() {
        headersService = new MapHeaderInterceptor();
    }

    @Test
    void testSetAndMethod_shouldBeEqual() {
        headersService.set(KEY, VALUE);
        assertEquals(VALUE, headersService.get(KEY));
    }

    @Test
    void testSetAndMethod_shouldNotBeEqual() {
        headersService.set(KEY, "val2");
        assertNotEquals(VALUE, headersService.get(KEY));
    }

    @Test
    void testSetAndMethod_shouldNotSetMDCKey() {
        headersService.set(KEY, VALUE);
        assertNull(MDC.get(KEY));
    }

    @Test
    void testSetAndMethod_shouldNotBeEqualToMDCValue() {
        headersService.set(KEY, "val2");
        assertNotEquals(VALUE, MDC.get(KEY));
    }
}
