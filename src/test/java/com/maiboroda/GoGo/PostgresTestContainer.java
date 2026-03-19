package com.maiboroda.GoGo;

import org.flywaydb.core.Flyway;
import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer {

    private static final PostgreSQLContainer<?> CONTAINER;

    static {
        CONTAINER = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("testdb")
                .withUsername("test")
                .withPassword("test");
        CONTAINER.start();

        Flyway.configure()
                .dataSource(
                        CONTAINER.getJdbcUrl(),
                        CONTAINER.getUsername(),
                        CONTAINER.getPassword()
                )
                .locations("classpath:db/migration")
                .cleanDisabled(false)
                .load()
                .migrate();
    }

    public static PostgreSQLContainer<?> getInstance() {
        return CONTAINER;
    }
}