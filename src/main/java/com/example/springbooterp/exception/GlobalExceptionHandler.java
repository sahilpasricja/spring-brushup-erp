package com.example.springbooterp.exception;

import com.example.springbooterp.service.EmployeeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Object> onMethodArgsNotValidException(ResponseStatusException e){
        Violation voilation = Violation.builder()
                .timestamp(Instant.now().getEpochSecond())
                .status(e.getStatus().value())
                .error(e.getStatus().getReasonPhrase())
                .message(e.getMessage())
                .build();
        System.out.println(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(voilation, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ResponseEntity<Object> onMiscException(Exception e){
        Violation voilation = Violation.builder()
                .timestamp(Instant.now().getEpochSecond())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase())
                .message(e.getMessage())
                .build();
        System.out.println(HttpStatus.BAD_REQUEST.getReasonPhrase());
        return new ResponseEntity<>(voilation, HttpStatus.BAD_REQUEST);
    }
}
