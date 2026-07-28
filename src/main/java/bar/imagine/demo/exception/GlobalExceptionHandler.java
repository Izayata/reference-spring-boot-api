package bar.imagine.demo.exception;

import bar.imagine.demo.exception.exceptions.InvalidPasswordException;
import bar.imagine.demo.exception.exceptions.RateLimitExceededException;
import bar.imagine.demo.exception.exceptions.TokenAlreadyUsedException;
import bar.imagine.demo.exception.exceptions.TokenExpiredException;
import bar.imagine.demo.exception.exceptions.TokenNotFoundException;
import jakarta.validation.ConstraintViolation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.TreeMap;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleTokenNotFoundException(TokenNotFoundException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, String>> handleInvalidPasswordException(InvalidPasswordException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    // These three describe a logically invalid but well-formed request (the token was valid at
    // some point but is expired/used/otherwise no longer acceptable) -> 410 Gone, not 400.
    @ExceptionHandler(TokenAlreadyUsedException.class)
    public ResponseEntity<Map<String, String>> handleTokenAlreadyUsedException(TokenAlreadyUsedException e) {
        return ResponseEntity
            .status(HttpStatus.GONE)
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleTokenExpiredException(TokenExpiredException e) {
        return ResponseEntity
            .status(HttpStatus.GONE)
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Map<String, String>> handleRateLimitExceededException(RateLimitExceededException e) {
        return ResponseEntity
            .status(HttpStatus.TOO_MANY_REQUESTS)
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    // A broad JVM exception representing a server-side programming error, not a client mistake -> 500.
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, String>> handleIllegalStateException(IllegalStateException e) {
        log.error("Unexpected server error", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.singletonMap("error", "An unexpected error occurred."));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = new TreeMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.computeIfAbsent(error.getField(), key -> new ArrayList<>()).add(error.getDefaultMessage());
        }

        errors.values().forEach(Collections::sort);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleConstraintViolationException(ConstraintViolationException ex) {
        Map<String, List<String>> errors = new TreeMap<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String path = violation.getPropertyPath().toString();
            String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;
            errors.computeIfAbsent(field, key -> new ArrayList<>()).add(violation.getMessage());
        }

        errors.values().forEach(Collections::sort);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", "Malformed request body."));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
            .status(HttpStatus.METHOD_NOT_ALLOWED)
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", "Invalid value for parameter: " + e.getName()));
    }

    @ExceptionHandler(com.google.i18n.phonenumbers.NumberParseException.class)
    public ResponseEntity<Map<String, String>> handleNumberParseException(com.google.i18n.phonenumbers.NumberParseException e) {
        return ResponseEntity
            .badRequest()
            .body(Collections.singletonMap("error", "Invalid phone number format: " + e.getMessage()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(Collections.singletonMap("error", "A conflict occurred: the provided data already exists."));
    }

    @ExceptionHandler({
        NoSuchElementException.class,
        EmptyResultDataAccessException.class,
        UsernameNotFoundException.class
    })
    public ResponseEntity<Map<String, String>> handleNotFoundExceptions(Exception e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("error", e.getMessage()));
    }

    // Thrown by DispatcherServlet when a request matches no handler and no static resource either
    // (e.g. a mistyped or unimplemented path) -> 404, not the generic 500 catch-all below.
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFoundException(NoResourceFoundException e) {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("error", "The requested resource was not found."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception e) {
        log.error("Unexpected error", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.singletonMap("error", "An unexpected error occurred."));
    }
}