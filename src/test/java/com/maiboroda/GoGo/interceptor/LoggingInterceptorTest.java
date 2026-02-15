package com.maiboroda.GoGo.interceptor;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggingInterceptorTest {

    private LoggingInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private Object handler;

    @BeforeEach
    void setUp() {
        interceptor = new LoggingInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        handler = new Object();
        MDC.clear();
    }

    @AfterEach
    void tearDown() {
        MDC.clear();
    }

    @Test
    void testPreHandle_ShouldPopulateMDC_WhenUserIsAuthenticated() {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);

        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn("Arthur");
        when(securityContext.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(securityContext);

        interceptor.preHandle(request, response, handler);

        assertNotNull(MDC.get("requestId"), "RequestId must be generated");
        assertEquals("Artur", MDC.get("user"), "Login must be 'Artur'");
    }

    @Test
    void testPreHandle_ShouldUseGuest_WhenNoAuthentication() {
        SecurityContextHolder.clearContext();

        interceptor.preHandle(request, response, handler);

        assertEquals("guest", MDC.get("user"), "For unauthorized persons there should be 'guest'");
    }

    @Test
    void testAfterCompletion_ShouldClearMDC() {
        MDC.put("requestId", "test-id");
        MDC.put("user", "test-user");

        interceptor.afterCompletion(request, response, handler, null);

        assertNull(MDC.get("requestId"));
        assertNull(MDC.get("user"));
    }
}