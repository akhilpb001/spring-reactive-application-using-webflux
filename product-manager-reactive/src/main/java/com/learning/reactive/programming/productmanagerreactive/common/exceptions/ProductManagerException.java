package com.learning.reactive.programming.productmanagerreactive.common.exceptions;

import org.springframework.http.HttpStatus;

public class ProductManagerException extends RuntimeException {

  private HttpStatus httpStatus;

  public ProductManagerException(HttpStatus httpStatus, String message) {
    super(message);
    this.httpStatus = httpStatus;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }
}
