package org.severinu.demoapi.api.interceptor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MDCHeadersServiceTest {

    private static final String KEY = "key1";
    private static final String VALUE = "val1";

    private MDCHeadersService headersService;

    @BeforeEach
    void setUp() {
        headersService = new MDCHeadersService();
    }


    @Test
    void testSetAndMethod_shouldBeEqual() {
        headersService.set(KEY, VALUE);
        assertEquals(VALUE, MDC.get(KEY));
    }

    @Test
    void testSetAndMethod_shouldNotBeEqual() {
        headersService.set(KEY, "val2");
        assertNotEquals(VALUE, MDC.get(KEY));
    }

}