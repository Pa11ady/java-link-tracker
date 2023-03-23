package ru.tinkoff.edu.java.bot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;

import java.util.Arrays;
import java.util.List;

@RestControllerAdvice

public class ErrorHandler {
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleBadRequest(RuntimeException e) {
        List<String> stacktrace = Arrays.stream(e.getStackTrace()).map(StackTraceElement::toString).toList();
        return new ApiErrorResponse(
                "Некорректные параметры запроса",
                "400",
                e.getClass().getName(),
                e.getMessage(),
                stacktrace
        );
    }
}
