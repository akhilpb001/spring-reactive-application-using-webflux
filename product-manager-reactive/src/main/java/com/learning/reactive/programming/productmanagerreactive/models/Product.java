package com.learning.reactive.programming.productmanagerreactive.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@Table("product_db.products")
public class Product {

  @Id
  private Long id;

  private String name;

  private String description;

  private Double price;

  public Product(String name, String description, Double price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }
}
