package com.learning.reactive.programming.productmanagerreactive.configs;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
public class ProductConfig {

  @Bean
  ConnectionFactoryInitializer initDB(@Qualifier("connectionFactory") ConnectionFactory factory) {
    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(factory);
    CompositeDatabasePopulator populator = new CompositeDatabasePopulator();
    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
//    populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("sampledata.sql")));
    initializer.setDatabasePopulator(populator);
    return initializer;
  }
}
