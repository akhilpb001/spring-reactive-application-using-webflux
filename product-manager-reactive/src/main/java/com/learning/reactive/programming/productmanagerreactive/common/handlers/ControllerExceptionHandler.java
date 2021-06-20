package com.learning.reactive.programming.productmanagerreactive.common.handlers;

import com.learning.reactive.programming.productmanagerreactive.common.exceptions.EntityDuplicateException;
import com.learning.reactive.programming.productmanagerreactive.common.exceptions.EntityNotFoundException;
import com.learning.reactive.programming.productmanagerreactive.common.exceptions.ProductManagerException;
import com.learning.reactive.programming.productmanagerreactive.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import reactor.core.publisher.Mono;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(EntityDuplicateException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleDuplicateException(EntityDuplicateException ex) {
    return Mono.just(new ResponseEntity(
        new ErrorResponse(400, ex.getMessage()),
        HttpStatus.BAD_REQUEST)
    );
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleEntityNotFoundException(EntityNotFoundException ex) {
    return Mono.just(new ResponseEntity(
        new ErrorResponse(404, ex.getMessage()),
        HttpStatus.NOT_FOUND)
    );
  }

  @ExceptionHandler(ProductManagerException.class)
  public Mono<ResponseEntity<ErrorResponse>> handleProductManagerException(ProductManagerException ex) {
    return Mono.just(new ResponseEntity(
        new ErrorResponse(ex.getHttpStatus().value(), ex.getMessage()),
        ex.getHttpStatus())
    );
  }
}
