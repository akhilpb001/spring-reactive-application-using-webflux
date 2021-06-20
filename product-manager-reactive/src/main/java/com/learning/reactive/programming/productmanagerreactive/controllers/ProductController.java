package com.learning.reactive.programming.productmanagerreactive.controllers;

import com.learning.reactive.programming.productmanagerreactive.models.Product;
import com.learning.reactive.programming.productmanagerreactive.models.ProductPrice;
import com.learning.reactive.programming.productmanagerreactive.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/products")
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public Flux<Product> findAll() {
    return productService.findAll();
  }

  @GetMapping(value = "/old/streaming", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
  public Flux<Product> findAllStreamingDeprecated() {
    return productService.findAll();
  }

  @GetMapping(value = "/new/streaming", produces = MediaType.APPLICATION_NDJSON_VALUE)
  public Flux<Product> findAllStreamingNew() {
    return productService.findAll();
  }

  @GetMapping("/{id}")
  public Mono<ResponseEntity<Product>> findById(@PathVariable Long id) {
    return productService
        .findById(id)
        .map(p -> ResponseEntity.ok().body(p));
  }

  @PostMapping
  public Mono<ResponseEntity<Product>> save(@RequestBody Product product) {
    return productService.save(product)
        .map(p -> ResponseEntity.ok().body(p));
  }

  @PutMapping("/{id}")
  public Mono<ResponseEntity<Product>> update(@PathVariable("id") Long id, @RequestBody Product product) {
    return productService.update(id, product)
        .map(p -> ResponseEntity.ok().body(p));
  }

  @DeleteMapping("/{id}")
  public Mono<Void> delete(@PathVariable("id") Long id) {
    return productService.delete(id);
  }

  @GetMapping("/{product}/prices")
  public Flux<ProductPrice> findAllPricesByProductV2(@PathVariable String product) {
    return productService.findProductPrices(product);
  }

  @GetMapping("/prices")
  public Flux<ProductPrice> findAllPricesByProduct(@RequestParam("product") String product) {
    return productService.findProductPrices(product);
  }
}
