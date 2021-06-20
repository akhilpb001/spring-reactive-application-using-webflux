package com.learning.reactive.programming.productmanagerreactive.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductQuote {

  private String shop;

  private String product;

  private Double price;

  private String discountCode;
}
