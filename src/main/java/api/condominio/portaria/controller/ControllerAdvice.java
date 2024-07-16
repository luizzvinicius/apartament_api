package api.condominio.portaria.controller;

import api.condominio.portaria.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ControllerAdvice {
    // Custom
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handle404(RecordNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(RegisterOverflow.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleRegisterOverflow(RegisterOverflow e) {
        return String.format("{'error': 'BAD_REQUEST', 'message': %s}", e.getMessage());
    }

    // HTTP
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public String methodNotAllowed() {
        return "{'error': 'Method_Not_Allowed',\n'message': 'Endpoint with this method is invalid'}";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String wrongPath(NoHandlerFoundException e) {
        return String.format("{'error': 'API path not found',%n'message': '%s'}", e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleJsonValueInvalid(MethodArgumentNotValidException ex) {
        var fields = ex.getFieldErrors().stream()
                .map(error -> String.format("'%s %s'", error.getField(), error.getDefaultMessage()))
                .reduce("", (c, e) -> c + e + ", \n");
        return String.format("{'error': 'invalid request pattern',%n'message': %s}", fields);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // type url param
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        if (e != null) {
            var types = e.getRequiredType().getName().split("\\.");
            return String.format("{'error': 'BAD_REQUEST',%n'message': '%s should be of type %s'}", e.getName(), types[types.length - 1]);
        }
        return "{'error': 'Bad BAD_REQUEST'}";
    }

    @ExceptionHandler(ConstraintViolationException.class) // url params
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationDTOs(ConstraintViolationException e) {
        var fields = e.getConstraintViolations().stream()
                .map(error -> String.format("'%s %s'", error.getPropertyPath(), error.getMessage()))
                .reduce("", (acc, error) -> acc + error + ", \n");
        return String.format("{'error': 'invalid request pattern',%n'message': %s}", fields);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // JSON value invalid
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String missingAttribute(HttpMessageNotReadableException e) {
        return String.format("{'error': 'BAD_REQUEST',%n'message': %s}", e.getMessage());
    }

    // Database
    @ExceptionHandler(DataIntegrityViolationException.class) // duplicate registers etc.
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String integrityViolation(DataIntegrityViolationException e) {
        return "{'error': 'BAD_REQUEST',\n'message': database error}";
    }
}