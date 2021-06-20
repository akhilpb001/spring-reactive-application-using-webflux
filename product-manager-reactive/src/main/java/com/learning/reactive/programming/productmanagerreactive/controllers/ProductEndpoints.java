package com.learning.reactive.programming.productmanagerreactive.controllers;

import com.learning.reactive.programming.productmanagerreactive.handlers.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductEndpoints {

  @Bean
  public RouterFunction<ServerResponse> routes(ProductHandler handler) {
    return route(GET("/v2/products").and(accept(APPLICATION_JSON)), handler::findAll)
        .andRoute(GET("/v2/products/{id}").and(accept(APPLICATION_JSON)), handler::findById);
  }

  @Bean
  public RouterFunction<ServerResponse> nestedRoutes(ProductHandler handler) {
    return nest(GET("/v3/products"),
        nest(accept(APPLICATION_JSON),
            route(GET(""), handler::findAll))
            .andRoute(GET("/{id}"), handler::findById));
  }
}
