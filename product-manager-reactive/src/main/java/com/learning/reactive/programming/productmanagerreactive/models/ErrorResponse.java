package com.learning.reactive.programming.productmanagerreactive.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {

  private int status;

  private String message;
}
