package com.maxijett.monetary.common.rest;

import com.maxijett.monetary.common.exception.MonetaryApiBusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    private final MessageSource messageSource;

    public RestExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception, Locale locale) {
        log.error("An error occurred! Details: ", exception);
        return new ResponseEntity<>(createErrorResponseFromMessageSource("common.system.error.occurred", locale), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpRequestMethodNotSupportedException methodNotSupportedException, Locale locale) {
        log.debug("HttpRequestMethodNotSupportedException occurred", methodNotSupportedException);
        return new ResponseEntity<>(createErrorResponseFromMessageSource("common.client.methodNotSupported", locale, methodNotSupportedException.getMethod()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleRequestPropertyBindingError(WebExchangeBindException webExchangeBindException, Locale locale) {
        log.debug("Bad request!", webExchangeBindException);
        return new ResponseEntity<>(createFieldErrorResponse(webExchangeBindException.getBindingResult(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleBindException(BindException bindException, Locale locale) {
        log.debug("Bad request!", bindException);
        return new ResponseEntity<>(createFieldErrorResponse(bindException.getBindingResult(), locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException, Locale locale) {
        log.trace("MethodArgumentTypeMismatchException occurred", methodArgumentTypeMismatchException);
        return new ResponseEntity<>(createErrorResponseFromMessageSource("common.client.typeMismatch", locale, methodArgumentTypeMismatchException.getName()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleInvalidArgumentException(MethodArgumentNotValidException methodArgumentNotValidException, Locale locale) {
        log.debug("Method argument not valid. Message: $methodArgumentNotValidException.message", methodArgumentNotValidException);
        return new ResponseEntity<>(createFieldErrorResponse(methodArgumentNotValidException.getBindingResult(), locale), HttpStatus.BAD_REQUEST);
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

    private ErrorResponse createFieldErrorResponse(BindingResult bindingResult, Locale locale) {
        List<String> requiredFieldErrorMessages = retrieveLocalizationMessage("common.client.requiredField", locale);
        String code = requiredFieldErrorMessages.get(0);

        String errorMessage = bindingResult
                .getFieldErrors().stream()
                .map(FieldError::getField)
                .map(error -> retrieveLocalizationMessage("common.client.requiredField", locale, error))
                .map(errorMessageList -> errorMessageList.get(1))
                .collect(Collectors.joining(" && "));

        log.debug("Exception occurred while request validation: {}", errorMessage);

        return new ErrorResponse(code, errorMessage);
    }

}
