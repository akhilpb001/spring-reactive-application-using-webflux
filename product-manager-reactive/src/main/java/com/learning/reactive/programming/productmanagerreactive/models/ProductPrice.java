package com.learning.reactive.programming.productmanagerreactive.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductPrice {

  private String shop;

  private Double originalPrice;

  private Double finalPrice;
}
