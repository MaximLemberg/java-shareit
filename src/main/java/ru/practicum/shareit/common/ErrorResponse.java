package ru.practicum.shareit.common;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.OffsetDateTime;

@Data
@Builder
@Value
@RequiredArgsConstructor
public class ErrorResponse {
    OffsetDateTime timestamp = OffsetDateTime.now();
    Integer code;
    String errorMessage;
}
