package com.maiboroda.GoGo;

import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.junit5.api.DBRider;
import com.maiboroda.GoGo.service.CountryService;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = com.maiboroda.GoGo.GoGoApplication.class
)
@AutoConfigureMockMvc
@DBRider
@DBUnit(
        caseSensitiveTableNames = true,
        qualifiedTableNames = false,
        schema = "public",
        cacheConnection = false,
        leakHunter = false
)
public abstract class AbstractIntegrationTest {

    static {
        System.setProperty("user.timezone", "UTC");
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("UTC"));
    }

    @Autowired
    private CountryService countryService;

    @AfterEach
    void refreshCountryCacheAfterTest() {
        countryService.refreshCache();
    }

    static final PostgreSQLContainer<?> postgreSQLContainer = PostgresTestContainer.getInstance();

    @DynamicPropertySource
    static void configurateProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.flyway.enabled", () -> "false");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }
}