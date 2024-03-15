package com.dodeuni.dodeuni.handler;

import static com.dodeuni.dodeuni.handler.ValidationError.getParameterName;

import com.dodeuni.dodeuni.except.NotFoundException;
import com.dodeuni.dodeuni.except.UnexpectedValueException;
import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (FieldError fieldError : e.getFieldErrors()) {
            log.error("name : {}, message : {}", fieldError.getField(), fieldError.getDefaultMessage());
            ValidationError exception = ValidationError.of(fieldError);
            errors.add(exception);
        }
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        List<ValidationError> errors = new ArrayList<>();
        for (ConstraintViolation constraintViolation : e.getConstraintViolations()) {
            String paramName = getParameterName(constraintViolation.getPropertyPath().toString());
            log.error("name : {}, message : {}", paramName, constraintViolation.getMessageTemplate());
            ValidationError exception = ValidationError.of(constraintViolation);
            errors.add(exception);
        }
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST, errors);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.DATA_NOT_FOUND);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @ExceptionHandler(UnexpectedValueException.class)
    protected ResponseEntity<ErrorResponse> handleUnexpectedValueException() {
        ErrorResponse response = new ErrorResponse(ErrorCode.BAD_REQUEST);
        log.error("name : {}, message : {}", response.getError(), response.getMessage());
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}