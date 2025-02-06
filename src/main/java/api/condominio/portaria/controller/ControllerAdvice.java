package api.condominio.portaria.controller;

import api.condominio.portaria.exceptions.InvalidEnumException;
import api.condominio.portaria.exceptions.RecordNotFoundException;
import api.condominio.portaria.exceptions.RegisterOverflowException;
import api.condominio.portaria.models.apiError.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestControllerAdvice
public class ControllerAdvice {
    // Custom
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handle404(RecordNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("NOT_FOUND", e.getMessage()));
    }

    @ExceptionHandler(RegisterOverflowException.class)
    public ResponseEntity<ApiErrorResponse> handleRegisterOverflowException(RegisterOverflowException e) {
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("BAD_REQUEST", e.getMessage()));
    }

    @ExceptionHandler(InvalidEnumException.class)
    public ResponseEntity<ApiErrorResponse> invalidEnumConvertion(InvalidEnumException e) {
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("BAD_REQUEST", e.getMessage()));
    }

    // HTTP
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiErrorResponse> methodNotAllowed() {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("Method_Not_Allowed", "Endpoint with this method is invalid"));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiErrorResponse> wrongPath(NoHandlerFoundException e) {
        return ResponseEntity.status(NOT_FOUND).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("API path not found", e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleJsonValueInvalid(MethodArgumentNotValidException ex) {
        var fields = ex.getFieldErrors().stream()
                .map(error -> String.format("'%s %s'", error.getField(), error.getDefaultMessage()))
                .reduce("", (c, e) -> c + e + ", \n");

        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("Invalid request pattern", fields));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class) // type url param
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        var types = new String[]{"Null"};
        var name = "Null";
        if (e != null) {
            types = e.getRequiredType().getName().split("\\.");
            name = e.getName();
        }

        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("BAD_REQUEST",
                        String.format("%s should be of type %s", name, types[types.length - 1])));
    }

    @ExceptionHandler(ConstraintViolationException.class) // url params
    public ResponseEntity<ApiErrorResponse> handleValidationDTOs(ConstraintViolationException e) {
        var fields = e.getConstraintViolations().stream()
                .map(error -> String.format("'%s %s'", error.getPropertyPath(), error.getMessage()))
                .reduce("", (acc, error) -> acc + error + ", \n");

        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("Invalid request pattern", fields));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // JSON value invalid
    public ResponseEntity<ApiErrorResponse> missingAttribute(HttpMessageNotReadableException e) {
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("BAD_REQUEST", e.getMessage()));
    }

    // Database
    @ExceptionHandler(DataIntegrityViolationException.class) // duplicate registers etc
    public ResponseEntity<ApiErrorResponse> integrityViolation(DataIntegrityViolationException e) {
        return ResponseEntity.status(BAD_REQUEST).contentType(APPLICATION_JSON)
                .body(new ApiErrorResponse("BAD_REQUEST", "database error"));
    }
}