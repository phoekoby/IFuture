package com.example.server.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String DB_URL;
    @Value("${spring.datasource.username}")
    private String DB_USERNAME;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Bean
    public void createMigrations(){
        final var flyway = Flyway
                .configure()
                .dataSource(DB_URL, DB_USERNAME, DB_PASSWORD)
                .locations("db")
                .load();
        flyway.migrate();
    }
}
