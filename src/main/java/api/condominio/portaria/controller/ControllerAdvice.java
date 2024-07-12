package api.condominio.portaria.controller;

import api.condominio.portaria.exceptions.RecordNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    // Custom
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(RecordNotFoundException e) {
        return e.getMessage();
    }

    // HTTP
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String methodNotAllowed() {
        return "{'error': 'Method Not Allowed',\n'message': 'Endpoint with this method is invalid'}";
    }

    // Database
    @ExceptionHandler(DataIntegrityViolationException.class) // registro duplicado etc.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String integrityViolation(DataIntegrityViolationException e) {
        return "{'error': 'Bad Request',\n'message': database error}";
    }
}