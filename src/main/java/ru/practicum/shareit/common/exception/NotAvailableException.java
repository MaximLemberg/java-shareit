package ru.practicum.shareit.common.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class NotAvailableException extends RuntimeException {

    public NotAvailableException(String message) {
        super(message);
    }
}