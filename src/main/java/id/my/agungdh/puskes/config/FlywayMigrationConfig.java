package id.my.agungdh.puskes.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

@Configuration
@Profile("migrator")
public class FlywayMigrationConfig {

    @Value("${app.clean-on-start:false}")
    private boolean clean;

    @Bean
    public Flyway flyway(DataSource dataSource) {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .cleanDisabled(!clean)
                .load();
        if (clean) {
            flyway.clean();
        }
        flyway.migrate();
        return flyway;
    }
}
