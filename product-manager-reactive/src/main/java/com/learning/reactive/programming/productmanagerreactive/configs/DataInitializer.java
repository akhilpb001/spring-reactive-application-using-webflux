package com.learning.reactive.programming.productmanagerreactive.configs;

import com.learning.reactive.programming.productmanagerreactive.models.Product;
import com.learning.reactive.programming.productmanagerreactive.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

  private final ProductRepository productRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    List<Product> products = new ArrayList() {{
      add(new Product("Product-A", "Product-A", 100.0));
      add(new Product("Product-B", "Product-B", 200.0));
      add(new Product("Product-C", "Product-C", 300.0));
      add(new Product("Product-D", "Product-D", 400.0));
      add(new Product("Product-E", "Product-E", 500.0));
    }};

    productRepository
        .deleteAll()
        .thenMany(Flux.fromIterable(products))
        .flatMap(p -> productRepository.save(p))
        .thenMany(productRepository.findAll())
        .subscribe(data -> {
          System.out.println("DataInitializer: " +  data);
        });
  }
}
