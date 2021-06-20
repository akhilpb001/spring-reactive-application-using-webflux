package com.learning.reactive.programming.productmanagerreactive.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Shop {

  private String name;

  private Double originalPrice;

  private Double finalPrice;
}
