package com.learning.reactive.programming.productmanagerreactive.services;

import com.learning.reactive.programming.productmanagerreactive.common.exceptions.EntityDuplicateException;
import com.learning.reactive.programming.productmanagerreactive.common.exceptions.EntityNotFoundException;
import com.learning.reactive.programming.productmanagerreactive.common.exceptions.ProductManagerException;
import com.learning.reactive.programming.productmanagerreactive.models.Product;
import com.learning.reactive.programming.productmanagerreactive.models.ProductPrice;
import com.learning.reactive.programming.productmanagerreactive.models.ProductQuote;
import com.learning.reactive.programming.productmanagerreactive.models.Shop;
import com.learning.reactive.programming.productmanagerreactive.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  public Flux<Product> findAll() {
    return productRepository.findAll();
  }

  public Mono<Product> findById(Long id) {
    return productRepository.findById(id)
        .switchIfEmpty(Mono.error(
            new ProductManagerException(HttpStatus.NOT_FOUND, "Product not found with the given id."))
        );
  }

  public Mono<Product> save(Product product) {
    return productRepository
        .findByName(product.getName())
        .defaultIfEmpty(product)
        .flatMap(p -> {
          if (p.getId() == null) {
            return productRepository.save(p);
          } else {
            return Mono.error(new EntityDuplicateException("Product with the given name already exist."));
          }
        });
  }

  public Mono<Product> update(Long id, Product product) {
    return productRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new EntityNotFoundException("Product not found with the given id.")))
        .flatMap(p -> {
          product.setId(id);
          return productRepository.save(product);
        });
  }

  public Mono<Void> delete(Long id) {
    return productRepository.deleteById(id);
  }

  public Flux<ProductPrice> findProductPrices(String product) {
    return findShops()
        .flatMap(s -> getProductQuote(product, s.getName()))
        .flatMap(q -> getProductPrice(q))
        .flatMap(p -> getExchangeRate().zipWith(Mono.just(p)))
        .map(t -> new ProductPrice(t.getT2().getShop(),
            t.getT2().getOriginalPrice(),
            t.getT2().getFinalPrice() * t.getT1()));
  }

  public Flux<Shop> findShops() {
    WebClient client = WebClient.create("http://localhost:8081/shops");

    return client
        .get()
        .retrieve().bodyToFlux(Shop.class);
  }

  private Mono<ProductQuote> getProductQuote(String product, String shop) {
    WebClient client = WebClient.create("http://localhost:8081/quotes");

    return client
        .get()
        .uri(req -> req.queryParam("shop", shop).queryParam("product", product).build())
        .retrieve().bodyToMono(ProductQuote.class);
  }

  private Mono<ProductPrice> getProductPrice(ProductQuote quote) {
    WebClient client = WebClient.create("http://localhost:8081/price");

    return client
        .get()
        .uri(req -> req.queryParam("shop", quote.getShop())
            .queryParam("product", quote.getProduct())
            .queryParam("price", quote.getPrice())
            .queryParam("discountCode", quote.getDiscountCode())
            .build())
        .retrieve().bodyToMono(ProductPrice.class);

  }

  private Mono<Double> getExchangeRate() {
    WebClient client = WebClient.create("http://localhost:8081/exchange");

    return client
        .get()
        .retrieve().bodyToMono(Double.class);
  }
}
