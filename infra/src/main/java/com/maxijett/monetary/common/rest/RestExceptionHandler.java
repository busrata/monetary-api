package com.maxijett.monetary.common.rest;

import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler {
    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MonetaryApiBusinessException.class)
    public ResponseEntity<ErrorResponse> handleMonetaryApiBusinessException(MonetaryApiBusinessException ex, Locale locale) {
        var response = createErrorResponseFromMessageSource(ex.getKey(), locale, ex.getArgs());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private ErrorResponse createErrorResponseFromMessageSource(String key, Locale locale, String... args) {
        List<String> messageList = retrieveLocalizationMessage(key, locale, args);
        return new ErrorResponse(messageList.get(0), messageList.get(1));
    }

    private List<String> retrieveLocalizationMessage(String key, Locale locale, String... args) {
        String message = messageSource.getMessage(key, args, locale);
        return Pattern.compile(";").splitAsStream(message).collect(Collectors.toList());
    }

}
