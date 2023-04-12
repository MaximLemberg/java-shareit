package ru.practicum.shareit.common.handler;

import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.WebRequest;
import ru.practicum.shareit.common.exception.ErrorResponse;
import ru.practicum.shareit.common.exception.UnsupportedStateException;

import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicationExceptionHandlerTest {

    ApplicationExceptionHandler applicationExceptionHandler = new ApplicationExceptionHandler();

    WebRequest webRequest = new WebRequest() {
        @Override
        public String getHeader(String headerName) {
            return null;
        }

        @Override
        public String[] getHeaderValues(String headerName) {
            return new String[0];
        }

        @Override
        public Iterator<String> getHeaderNames() {
            return null;
        }

        @Override
        public String getParameter(String paramName) {
            return null;
        }

        @Override
        public String[] getParameterValues(String paramName) {
            return new String[0];
        }

        @Override
        public Iterator<String> getParameterNames() {
            return null;
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return null;
        }

        @Override
        public Locale getLocale() {
            return null;
        }

        @Override
        public String getContextPath() {
            return null;
        }

        @Override
        public String getRemoteUser() {
            return null;
        }

        @Override
        public Principal getUserPrincipal() {
            return null;
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }

        @Override
        public boolean isSecure() {
            return false;
        }

        @Override
        public boolean checkNotModified(long lastModifiedTimestamp) {
            return false;
        }

        @Override
        public boolean checkNotModified(String etag) {
            return false;
        }

        @Override
        public boolean checkNotModified(String etag, long lastModifiedTimestamp) {
            return false;
        }

        @Override
        public String getDescription(boolean includeClientInfo) {
            return null;
        }

        @Override
        public Object getAttribute(String name, int scope) {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value, int scope) {

        }

        @Override
        public void removeAttribute(String name, int scope) {

        }

        @Override
        public String[] getAttributeNames(int scope) {
            return new String[0];
        }

        @Override
        public void registerDestructionCallback(String name, Runnable callback, int scope) {

        }

        @Override
        public Object resolveReference(String key) {
            return null;
        }

        @Override
        public String getSessionId() {
            return null;
        }

        @Override
        public Object getSessionMutex() {
            return null;
        }
    };

    @Test
    void handleMethodArgumentNotValidExceptionTest() {
        ErrorResponse errorResponse = applicationExceptionHandler.handleMethodArgumentNotValidException();
        assertEquals(400, (int) errorResponse.getCode());
    }

    @Test
    void handleValidateExTest() {
        ErrorResponse errorResponse = applicationExceptionHandler.handleValidateEx(new RuntimeException(), webRequest);
        assertEquals(400, (int) errorResponse.getCode());
        assertEquals("Incorrect data", errorResponse.getError());
    }

    @Test
    void handleNotAvailableExceptionTest() {
        ErrorResponse errorResponse = applicationExceptionHandler.handleNotAvailableException();
        assertEquals(400, (int) errorResponse.getCode());
    }

    @Test
    void handleMethodArgumentEntityNotFoundExceptionTest() {
        ErrorResponse errorResponse = applicationExceptionHandler.handleMethodArgumentEntityNotFoundException();
        assertEquals(404, (int) errorResponse.getCode());
    }

    @Test
    void handleMethodAUnsupportedStateExceptionTest() {
        ErrorResponse errorResponse = applicationExceptionHandler.handleMethodAUnsupportedStateException(new UnsupportedStateException(""));
        assertEquals(400, (int) errorResponse.getCode());
    }


}
