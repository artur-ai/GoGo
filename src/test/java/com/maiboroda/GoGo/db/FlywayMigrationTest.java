package com.maiboroda.GoGo.db;

import com.maiboroda.GoGo.AbstractIntegrationTest;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import static org.assertj.core.api.Assertions.assertThat;


public class FlywayMigrationTest extends AbstractIntegrationTest {
    @Autowired
    private DataSource dataSource;

    @Test
    void testApplyAllMigration() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .load();

        var info = flyway.info();

        assertThat(info.all()).hasSizeGreaterThanOrEqualTo(3);
        assertThat(info.pending()).isEmpty();
    }
}
