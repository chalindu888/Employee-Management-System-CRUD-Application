package com.example.employee.exception;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Handles exceptions from every REST controller in one place.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFound(
            EmployeeNotFoundException exception,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                error(
                    404,
                    "Not Found",
                    exception.getMessage(),
                    request
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> validation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request) {

        Map<String, String> fields = new LinkedHashMap<String, String>();

        // Return one validation message for each invalid field.
        for (FieldError field : exception.getBindingResult().getFieldErrors()) {
            fields.putIfAbsent(
                    field.getField(),
                    field.getDefaultMessage()
            );
        }

        Map<String, Object> body = error(
                400,
                "Bad Request",
                "Validation failed",
                request
        );

        body.put("fieldErrors", fields);

        return ResponseEntity.badRequest().body(body);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> invalidJson(
            HttpServletRequest request) {

        return ResponseEntity.badRequest().body(error(
                400,
                "Bad Request",
                "Request body is missing or contains invalid JSON or field types",
                request
        ));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> invalidId(
            HttpServletRequest request) {

        return ResponseEntity.badRequest().body(error(
                400,
                "Bad Request",
                "Employee id must be a valid integer",
                request
        ));
    }

    private Map<String, Object> error(
            int status,
            String title,
            String message,
            HttpServletRequest request) {

        Map<String, Object> body = new LinkedHashMap<String, Object>();

        body.put("status", status);
        body.put("error", title);
        body.put("message", message);
        body.put("path", request.getRequestURI());

        return body;
    }
}