package com.learning.reactive.programming.productmanagerreactive.handlers;

import com.learning.reactive.programming.productmanagerreactive.models.Product;
import com.learning.reactive.programming.productmanagerreactive.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

  private final ProductService productService;

  public Mono<ServerResponse> findAll(ServerRequest request) {
    Flux<Product> products = productService.findAll();
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(products, Product.class);
  }

  public Mono<ServerResponse> findById(ServerRequest req) {
    String id = req.pathVariable("id");
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();
    return ServerResponse
        .ok()
        .body(productService.findById(Long.parseLong(id)), Product.class)
        .switchIfEmpty(notFound);
  }

}
